$(function() {
    const rowData = getRowData();

    const gridOptions = {
        rowData: rowData,
        columnDefs: [
            { field: 'time', width: 100 },
            { field: 'temp1', width: 100 },
            { field: 'temp2', width: 100 },
            { field: 'temp3', width: 100 },
            { field: 'ror', width: 100 },
            { field: 'heater', width: 100 },
            { field: 'fan', width: 100 },
            { field: 'note', width: 100 },
        ],
        suppressColumnVirtualisation: true,
        suppressRowVirtualisation: true,
    };

    // Create Grid: Create new grid within the #myGrid div, using the Grid Options object
    let gridApi = agGrid.createGrid(document.querySelector('#myGrid'), gridOptions);
})

function getRowData() {
    let rowData = [];
    for (let i = 0; i < dataSize; i++) {
        let row = {
            time: timeArr[i],
            temp1: temp1Arr[i],
            temp2: temp2Arr[i],
            temp3: temp3Arr[i],
            ror: rorArr[i],
            heater: heaterArr[i],
            fan: fanArr[i],
            note: '',
        };
        rowData.push(row);
    }
    return rowData;
}


