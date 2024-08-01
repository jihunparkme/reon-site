am5.ready(function () {
    const dtrRoot = am5.Root.new("dtrBar");
    const dtrTheme = am5.Theme.new(dtrRoot);

    dtrTheme.rule("Grid", ["base"]).setAll({
        strokeOpacity: 0.1
    });

    const dtrGradient = am5.LinearGradient.new(dtrRoot, {
        stops: [{
            color: am5.color(0x000000)
        }, {
            color: am5.color(0x000000)
        }]
    });

    const dtrChart = dtrRoot.container.children.push(am5xy.XYChart.new(dtrRoot, {
        panX: false,
        panY: false,
        paddingLeft: 0,
        layout: dtrRoot.verticalLayout,
        background: am5.Rectangle.new(dtrRoot, {
            fillGradient: dtrGradient
        })
    }));

    dtrChart.set("scrollbarY", am5.Scrollbar.new(dtrRoot, {
        orientation: "vertical"
    }));

    const dtrData = [
        {
            "record": "Record2",
            "dtr1": recordObjs[1].dtrFirstPercent,
            "dtr2": recordObjs[1].dtrSecondPercent,
            "dtr3": recordObjs[1].dtrThirdPercent
        }, {
            "record": "Record1",
            "dtr1": recordObjs[0].dtrFirstPercent,
            "dtr2": recordObjs[0].dtrSecondPercent,
            "dtr3": recordObjs[0].dtrThirdPercent
        }]

    dtrChart.topAxesContainer.children.push(am5.Label.new(dtrRoot, {
        text: "DTR",
        fontSize: 20,
        fontWeight: "400",
        fill: am5.color(0xFFFFFF),
        x: am5.p50,
        centerX: am5.p50
    }));

    const dtrYRenderer = am5xy.AxisRendererY.new(dtrRoot, {});
    const dtrYAxis = dtrChart.yAxes.push(am5xy.CategoryAxis.new(dtrRoot, {
        categoryField: "record",
        renderer: dtrYRenderer,
        tooltip: am5.Tooltip.new(dtrRoot, {})
    }));

    dtrYRenderer.grid.template.setAll({
        location: 1
    })

    dtrYAxis.data.setAll(dtrData);

    const dtrYRendererColor = dtrYAxis.get("renderer");
    dtrYRendererColor.labels.template.setAll({
        fill: am5.color(0xffffff),
    });

    const dtrXAxis = dtrChart.xAxes.push(am5xy.ValueAxis.new(dtrRoot, {
        min: 0,
        max: 100,
        strictMinMax: true,
        maxPrecision: 0,
        renderer: am5xy.AxisRendererX.new(dtrRoot, {
            minGridDistance: 40,
            strokeOpacity: 0.1
        })
    }));


    const dtrXRenderer = dtrXAxis.get("renderer");
    dtrXRenderer.labels.template.setAll({
        fill: am5.color(0xffffff),
    });

    function makeSeries(name, fieldName) {
        var dtrSeries = dtrChart.series.push(am5xy.ColumnSeries.new(dtrRoot, {
            name: name,
            stacked: true,
            xAxis: dtrXAxis,
            yAxis: dtrYAxis,
            baseAxis: dtrYAxis,
            valueXField: fieldName,
            categoryYField: "record"
        }));
        dtrSeries.columns.template.setAll({
            tooltipText: "{name}, {valueX}%",
            tooltipY: am5.percent(90)
        });

        dtrSeries.data.setAll(dtrData);

        dtrSeries.bullets.push(function () {
            return am5.Bullet.new(dtrRoot, {
                sprite: am5.Label.new(dtrRoot, {
                    text: "{valueX}",
                    fill: dtrRoot.interfaceColors.get("alternativeText"),
                    centerY: am5.p50,
                    centerX: am5.p50,
                    populateText: true
                })
            });
        });
    }

    makeSeries("Area1", "dtr1");
    makeSeries("Area2", "dtr2");
    makeSeries("Area3", "dtr3");
});