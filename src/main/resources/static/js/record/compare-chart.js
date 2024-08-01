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
        am5.color("#FF0000"),
        am5.color("#FF0000"),
        am5.color("#FF0000"),

        am5.color("#0000FF"),
        am5.color("#0000FF"),
        am5.color("#0000FF"),
        am5.color("#0000FF")
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

    let dataSize = Math.max(recordObjs[0].temp1Arr.length, recordObjs[1].temp1Arr.length);
    let data = [];
    for (let i = 0; i < dataSize; i++) {
        data.push(
            {
                second: recordObjs[mainIdx].timeArr[i],

                temp1: recordObjs[0].temp1Arr[i],
                temp2: recordObjs[0].temp2Arr[i],
                temp3: recordObjs[0].temp3Arr[i],
                temp4: recordObjs[0].temp4Arr[i],
                ror: recordObjs[0].rorArr[i],

                temp1_2: recordObjs[1].temp1Arr[i],
                temp2_2: recordObjs[1].temp2Arr[i],
                temp3_2: recordObjs[1].temp3Arr[i],
                temp4_2: recordObjs[1].temp4Arr[i],
                ror_2: recordObjs[1].rorArr[i]
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
                legendValueText: "{valueY}"
            })
        );

        series.data.setAll(data);
        series.appear(1000);
    }

    createSeries("R1_Drum", "temp1");
    createSeries("R1_Heater", "temp2");
    createSeries("R1_Pesudo Bean", "temp3");
    createSeries("R1_ROR", "ror");

    createSeries("R2_Drum", "temp1_2");
    createSeries("R2_Heater", "temp2_2");
    createSeries("R2_Pesudo Bean", "temp3_2");
    createSeries("R2_ROR", "ror_2");

    // Add scrollbar
    chart.set("scrollbarX", am5.Scrollbar.new(root, {
        orientation: "horizontal"
    }));

    chart.set("scrollbarY", am5.Scrollbar.new(root, {
        orientation: "vertical"
    }));

    // Add legend
    let legend = chart.rightAxesContainer.children.push(am5.Legend.new(root, {
        width: 230,
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
        textAlign: "right",
        fill: am5.color(0xffffff)
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
        am5.color("#FF0000"),
        am5.color("#FF0000"),
        am5.color("#FF0000"),

        am5.color("#0000FF"),
        am5.color("#0000FF"),
        am5.color("#0000FF")
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

    let dataSizeOfSensor = Math.max(recordObjs[0].heaterArr.length, recordObjs[1].heaterArr.length);
    let dataOfSensor = [];
    for (let i = 0; i < dataSizeOfSensor; i++) {
        dataOfSensor.push(
            {
                second: recordObjs[mainIdx].timeArr[i],

                heater: recordObjs[0].heaterArr[i],
                fan: recordObjs[0].fanArr[i],
                fan2: recordObjs[0].fan2Arr[i],

                heater_2: recordObjs[1].heaterArr[i],
                fan_2: recordObjs[1].fanArr[i],
                fan2_2: recordObjs[1].fan2Arr[i],
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
                legendValueText: "{valueY}"
            })
        );

        seriesOfSensor.data.setAll(dataOfSensor);
        seriesOfSensor.appear(1000);
    }

    createSeriesOfSensor("R1_Heater", "heater");
    createSeriesOfSensor("R1_Fan", "fan");
    createSeriesOfSensor("R1_Fan2", "fan2");

    createSeriesOfSensor("R2_Heater", "heater_2");
    createSeriesOfSensor("R2_Fan", "fan_2");
    createSeriesOfSensor("R2_Fan2", "fan2_2");

    // Add scrollbar
    chartOfSensor.set("scrollbarX", am5.Scrollbar.new(rootOfSensor, {
        orientation: "horizontal"
    }));

    chartOfSensor.set("scrollbarY", am5.Scrollbar.new(rootOfSensor, {
        orientation: "vertical"
    }));

    // Add legend
    let legendOfSensor = chartOfSensor.rightAxesContainer.children.push(am5.Legend.new(rootOfSensor, {
        width: 230,
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
        textAlign: "right",
        fill: am5.color(0xffffff)
    });

    // It's is important to set legend data after all the events are set on template, otherwise events won't be copied
    legendOfSensor.data.setAll(chartOfSensor.series.values);

    // Make stuff animate on load
    chartOfSensor.appear(1000, 100);
});