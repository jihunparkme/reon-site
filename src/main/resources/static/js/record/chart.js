am5.ready(function() {

    /***********************************************************************************************************
     * Temperature Chart
     */
        // Create root element
    let root = am5.Root.new("temperatureChart");
    const myTheme = am5.Theme.new(root);

    myTheme.rule("AxisLabel", ["minor"]).setAll({
        dy:1
    });

    myTheme.rule("Grid", ["x"]).setAll({
        strokeOpacity: 0.05
    });

    myTheme.rule("Grid", ["x", "minor"]).setAll({
        strokeOpacity: 0.05
    });

    // Set themes
    root.setThemes([
        am5themes_Animated.new(root),
        myTheme
    ]);

    // Create chart
    let gradient = am5.LinearGradient.new(root, { // chart background color
        stops: [{
            color: am5.color(0x000000)
        }, {
            color: am5.color(0x000000)
        }, {
            color: am5.color(0x000000)
        }]
    });

    let chart = root.container.children.push(
        am5xy.XYChart.new(root, {
            panX: true,
            panY: true,
            wheelX: "panX",
            wheelY: "zoomX",
            layout: root.verticalLayout,
            pinchZoomX:true,
            background: am5.Rectangle.new(root, {
                fillGradient: gradient
            })
        })
    );

    // set chart color
    chart.get("colors").set("colors", [
        am5.color("#FF0000"),
        am5.color("#0000FF"),
        am5.color("#FFFF00"),
        am5.color("#008000")
    ]);

    chart.topAxesContainer.children.push(am5.Label.new(root, { // chart title
        text: "Temperature & RoR",
        fontSize: 20,
        fontWeight: "400",
        fill: am5.color(0xFFFFFF), // chart title color
        x: am5.p50,
        centerX: am5.p50
    }));

    // Add cursor
    let cursor = chart.set("cursor", am5xy.XYCursor.new(root, {
        behavior: "none"
    }));
    cursor.lineY.set("visible", false);

    cursor.lineY.setAll({ // mouse over line y color
        stroke: am5.color(0xFFFFFF)
    });
    cursor.lineX.setAll({ // mouse over line x color
        stroke: am5.color(0xFFFFFF)
    });

    let dataSize = 0;
    if (record.temp1 !== null) {
        dataSize = JSON.parse(record.temp1).length;
    }

    let temp1Arr = generateGraphData(record.temp1, dataSize);
    let temp2Arr = generateGraphData(record.temp2, dataSize);
    let temp3Arr = generateGraphData(record.temp3, dataSize);
    let rorArr = generateGraphData(record.ror, dataSize);

    let data = [];
    for (let i = 0; i < dataSize; i++) {
        let min = parseInt((i + 1) / 60);
        let sec = (min > 0 ? parseInt((i + 1) / 60) : "") + "′" + (i + 1) % 60;
        data.push(
            {
                second: sec,
                temp1: temp1Arr[i],
                temp2: temp2Arr[i],
                temp3: temp3Arr[i],
                ror: rorArr[i]
            }
        );
    }

    // Create axes
    let xAxis = chart.xAxes.push(
        am5xy.CategoryAxis.new(root, {
            categoryField: "second",
            renderer: am5xy.AxisRendererX.new(root, {}),
            tooltip: am5.Tooltip.new(root, {})
        })
    );

    xAxis.data.setAll(data);

    let yAxis = chart.yAxes.push(
        am5xy.ValueAxis.new(root, {
            maxPrecision: 0,
            renderer: am5xy.AxisRendererY.new(root, {})
        })
    );

    xAxis.children.push( // xAxis title
        am5.Label.new(root, {
            text: "seconds",
            x: am5.p50,
            centerX:am5.p50,
            fill: am5.color(0xFFFFFF)
        })
    );

    yAxis.children.unshift(  // yAxis title
        am5.Label.new(root, {
            rotation: -90,
            text: "value",
            y: am5.p50,
            centerX: am5.p50,
            fill: am5.color(0xFFFFFF)
        })
    );

    let xRenderer = xAxis.get("renderer"); // X Axis number info
    xRenderer.labels.template.setAll({
        fill: am5.color(0xffffff),
    });

    xAxis.get("renderer").grid.template.setAll({ // x축 눈금색
        stroke: am5.color(0xFFFFFF),
        strokeWidth: 1,
        strokeOpacity: 0.3
    });

    let yRenderer = yAxis.get("renderer"); // X Axis number info
    yRenderer.ticks.template.setAll({
        minPosition: 0.1,
        visible: true,
        fill: am5.color(0xffffff),
    });
    yRenderer.labels.template.setAll({
        minPosition: 0.1,
        fill: am5.color(0xffffff),
    });
    yRenderer.grid.template.setAll({ // y축 눈금색
        stroke: am5.color(0xFFFFFF),
        strokeWidth: 1,
        strokeOpacity: 0.3
    });

    function createSeries(name, field) {
        let series = chart.series.push(
            am5xy.LineSeries.new(root, {
                name: name,
                xAxis: xAxis,
                yAxis: yAxis,
                valueYField: field,
                categoryXField: "second",
                legendValueText: "{valueY}",
                tooltip: am5.Tooltip.new(root, {
                    pointerOrientation: "horizontal",
                    labelText: "[bold]{name}: {valueY}"
                })
            })
        );

        series.data.setAll(data);
        series.appear(1000);
    }

    createSeries("Temp1", "temp1");
    createSeries("Temp2", "temp2");
    createSeries("Temp3", "temp3");
    createSeries("ROR", "ror");

    // Add scrollbar
    chart.set("scrollbarX", am5.Scrollbar.new(root, {
        orientation: "horizontal"
    }));

    chart.set("scrollbarY", am5.Scrollbar.new(root, {
        orientation: "vertical"
    }));

    // Add legend
    let legend = chart.rightAxesContainer.children.push(am5.Legend.new(root, {
        width: 200,
        paddingLeft: 15,
        height: am5.percent(100)
    }));

    legend.labels.template.setAll({ // 목차 생상 설정
        fill: am5.color(0xFFFFFF)
    });

    // When legend item container is hovered, dim all the series except the hovered one
    legend.itemContainers.template.events.on("pointerover", function(e) {
        let itemContainer = e.target;

        // As series list is data of a legend, dataContext is series
        let series = itemContainer.dataItem.dataContext;

        chart.series.each(function(chartSeries) {
            if (chartSeries != series) {
                chartSeries.strokes.template.setAll({
                    strokeOpacity: 0.15,
                    stroke: am5.color(0x000000)
                });
            } else {
                chartSeries.strokes.template.setAll({
                    strokeWidth: 3
                });
            }
        })
    })

    // When legend item container is unhovered, make all series as they are
    legend.itemContainers.template.events.on("pointerout", function(e) {
        let itemContainer = e.target;
        let series = itemContainer.dataItem.dataContext;

        chart.series.each(function(chartSeries) {
            chartSeries.strokes.template.setAll({
                strokeOpacity: 1,
                strokeWidth: 1,
                stroke: chartSeries.get("fill")
            });
        });
    })

    legend.itemContainers.template.set("width", am5.p100);
    legend.valueLabels.template.setAll({
        width: am5.p100,
        textAlign: "right"
    });

    // It's is important to set legend data after all the events are set on template, otherwise events won't be copied
    legend.data.setAll(chart.series.values);

    // Make stuff animate on load
    chart.appear(1000, 100);


    /***********************************************************************************************************
     * Sensor Chart
     */
        // Create root element
    let rootOfSensor = am5.Root.new("sensorChart");
    const myThemeOfSensor = am5.Theme.new(rootOfSensor);

    myThemeOfSensor.rule("AxisLabel", ["minor"]).setAll({
        dy:1
    });

    myThemeOfSensor.rule("Grid", ["x"]).setAll({
        strokeOpacity: 0.05
    });

    myThemeOfSensor.rule("Grid", ["x", "minor"]).setAll({
        strokeOpacity: 0.05
    });

    // Set themes
    rootOfSensor.setThemes([
        am5themes_Animated.new(rootOfSensor),
        myThemeOfSensor
    ]);

    let gradientOfChart = am5.LinearGradient.new(rootOfSensor, {
        stops: [{
            color: am5.color(0x000000)
        }, {
            color: am5.color(0x000000)
        }, {
            color: am5.color(0x000000)
        }]
    });

    // Create chart
    let chartOfSensor = rootOfSensor.container.children.push(
        am5xy.XYChart.new(rootOfSensor, {
            panX: true,
            panY: true,
            wheelX: "panX",
            wheelY: "zoomX",
            layout: rootOfSensor.verticalLayout,
            pinchZoomX:true,
            background: am5.Rectangle.new(rootOfSensor, {
                fillGradient: gradientOfChart
            })
        })
    );

    // set chart color
    chartOfSensor.get("colors").set("colors", [
        am5.color("#FF7F00"),
        am5.color("#8B00FF"),
    ]);

    chartOfSensor.topAxesContainer.children.push(am5.Label.new(rootOfSensor, {
        text: "Sensors",
        fontSize: 20,
        fontWeight: "400",
        fill: am5.color(0xFFFFFF),
        x: am5.p50,
        centerX: am5.p50
    }));

    // Add cursor
    let cursorOfSensor = chartOfSensor.set("cursor", am5xy.XYCursor.new(rootOfSensor, {
        behavior: "none"
    }));
    cursorOfSensor.lineY.set("visible", false);

    cursorOfSensor.lineY.setAll({
        stroke: am5.color(0xFFFFFF)
    });
    cursorOfSensor.lineX.setAll({
        stroke: am5.color(0xFFFFFF)
    });

    let dataSizeOfSensor = 0;
    if (record.heater !== null) {
        dataSizeOfSensor = JSON.parse(record.heater).length;
    }

    let heaterArr = generateGraphData(record.heater, dataSizeOfSensor);
    let fanArr = generateGraphData(record.fan, dataSizeOfSensor);
    console.log(heaterArr);
    console.log(fanArr);

    let dataOfSensor = [];
    for (let i = 0; i < dataSizeOfSensor; i++) {
        let minOfSensor = parseInt((i + 1) / 60);
        let secOfSensor = (minOfSensor > 0 ? parseInt((i + 1) / 60) : "") + "′" + (i + 1) % 60;
        dataOfSensor.push(
            {
                second: secOfSensor,
                heater: heaterArr[i],
                fan: fanArr[i],
            }
        );
    }

    // Create axes
    let xAxisOfSensor = chartOfSensor.xAxes.push(
        am5xy.CategoryAxis.new(rootOfSensor, {
            categoryField: "second",
            renderer: am5xy.AxisRendererX.new(rootOfSensor, {}),
            tooltip: am5.Tooltip.new(rootOfSensor, {})
        })
    );

    xAxisOfSensor.data.setAll(dataOfSensor);

    let yAxisOfSensor = chartOfSensor.yAxes.push(
        am5xy.ValueAxis.new(rootOfSensor, {
            maxPrecision: 0,
            renderer: am5xy.AxisRendererY.new(rootOfSensor, {})
        })
    );

    xAxisOfSensor.children.push(
        am5.Label.new(rootOfSensor, {
            text: "seconds",
            x: am5.p50,
            centerX:am5.p50,
            fill: am5.color(0xFFFFFF)
        })
    );

    yAxisOfSensor.children.unshift(
        am5.Label.new(rootOfSensor, {
            rotation: -90,
            text: "value",
            y: am5.p50,
            centerX: am5.p50,
            fill: am5.color(0xFFFFFF)
        })
    );

    let xRendererOfSensor = xAxisOfSensor.get("renderer");
    xRendererOfSensor.labels.template.setAll({
        fill: am5.color(0xffffff),
    });

    xAxisOfSensor.get("renderer").grid.template.setAll({
        stroke: am5.color(0xFFFFFF),
        strokeWidth: 1,
        strokeOpacity: 0.3
    });

    let yRendererOfSensor = yAxisOfSensor.get("renderer");
    yRendererOfSensor.ticks.template.setAll({
        minPosition: 0.1,
        visible: true,
        fill: am5.color(0xffffff)
    });
    yRendererOfSensor.labels.template.setAll({
        minPosition: 0.1,
        fill: am5.color(0xffffff)
    });
    yRendererOfSensor.grid.template.setAll({
        stroke: am5.color(0xFFFFFF),
        strokeWidth: 1,
        strokeOpacity: 0.3
    });

    function createSeriesOfSensor(name, field) {
        let seriesOfSensor = chartOfSensor.series.push(
            am5xy.LineSeries.new(rootOfSensor, {
                name: name,
                xAxis: xAxisOfSensor,
                yAxis: yAxisOfSensor,
                valueYField: field,
                categoryXField: "second",
                legendValueText: "{valueY}",
                tooltip: am5.Tooltip.new(rootOfSensor, {
                    pointerOrientation: "horizontal",
                    labelText: "[bold]{name}: {valueY}"
                })
            })
        );

        seriesOfSensor.data.setAll(dataOfSensor);
        seriesOfSensor.appear(1000);
    }

    createSeriesOfSensor("Heater", "heater");
    createSeriesOfSensor("Fan", "fan");

    // Add scrollbar
    chartOfSensor.set("scrollbarX", am5.Scrollbar.new(rootOfSensor, {
        orientation: "horizontal"
    }));

    chartOfSensor.set("scrollbarY", am5.Scrollbar.new(rootOfSensor, {
        orientation: "vertical"
    }));

    // Add legend
    let legendOfSensor = chartOfSensor.rightAxesContainer.children.push(am5.Legend.new(rootOfSensor, {
        width: 200,
        paddingLeft: 15,
        height: am5.percent(100)
    }));

    legendOfSensor.labels.template.setAll({
        fill: am5.color(0xFFFFFF)
    });

    // When legend item container is hovered, dim all the series except the hovered one
    legendOfSensor.itemContainers.template.events.on("pointerover", function(e) {
        let itemContainerOfSensor = e.target;

        // As series list is data of a legend, dataContext is series
        let seriesOfSensor = itemContainerOfSensor.dataItem.dataContext;

        chartOfSensor.series.each(function(chartSeries) {
            if (chartSeries != seriesOfSensor) {
                chartSeries.strokes.template.setAll({
                    strokeOpacity: 0.15,
                    stroke: am5.color(0x000000)
                });
            } else {
                chartSeries.strokes.template.setAll({
                    strokeWidth: 3
                });
            }
        })
    })

    // When legend item container is unhovered, make all series as they are
    legendOfSensor.itemContainers.template.events.on("pointerout", function(e) {
        chartOfSensor.series.each(function(chartSeries) {
            chartSeries.strokes.template.setAll({
                strokeOpacity: 1,
                strokeWidth: 1,
                stroke: chartSeries.get("fill")
            });
        });
    })

    legendOfSensor.itemContainers.template.set("width", am5.p100);
    legendOfSensor.valueLabels.template.setAll({
        width: am5.p100,
        textAlign: "right"
    });

    // It's is important to set legend data after all the events are set on template, otherwise events won't be copied
    legendOfSensor.data.setAll(chartOfSensor.series.values);

    // Make stuff animate on load
    chartOfSensor.appear(1000, 100);
});

function generateGraphData(data, size) {
    if (data === null) {
        return Array(size).fill(0);
    }

    return JSON.parse(data);
}