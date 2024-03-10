$(function() {
    const rowData = getRowData();

    const gridOptions = {
        rowData: rowData,
        columnDefs: [
            { field: 'time', width: 100 },
            { field: 'DrumBottom', width: 130 },
            { field: 'DrumTop', width: 120 },
            { field: 'Exhaust', width: 120 },
            { field: 'Board', width: 120 },
            { field: 'ror', width: 100 },
            { field: 'heater', width: 100 },
            { field: 'fan', width: 100 },
            { field: 'note', width: 150 },
        ],
    };

    // Create Grid: Create new grid within the #myGrid div, using the Grid Options object
    let gridApi = agGrid.createGrid(document.querySelector('#myGrid'), gridOptions);
})

function getRowData() {
    let rowData = [];
    for (let i = 0; i < dataSize; i++) {
        let row = {
            time: timeArr[i],
            DrumBottom: temp1Arr[i],
            DrumTop: temp2Arr[i],
            Exhaust: temp3Arr[i],
            Board: temp4Arr[i],
            ror: rorArr[i],
            heater: heaterArr[i],
            fan: fanArr[i],
            note: noteArr[i],
        };
        rowData.push(row);
    }
    return rowData;
}


