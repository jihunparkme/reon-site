$(function() {
    const rowData = getRowData();

    const gridOptions = {
        rowData: rowData,
        columnDefs: [
            { field: 'time' },
            { field: 'temp1' },
            { field: 'temp2' },
            { field: 'temp3' },
            { field: 'ror' },
            { field: 'heater' },
            { field: 'fan' },
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
            time: secondsToMMSS(i),
            temp1: temp1Arr[i],
            temp2: temp2Arr[i],
            temp3: temp3Arr[i],
            ror: rorArr[i],
            heater: heaterArr[i],
            fan: fanArr[i],
        };
        rowData.push(row);
    }
    return rowData;
}

function secondsToMMSS(seconds) {
    const minutes = Math.floor(seconds / 60);
    const remainingSeconds = seconds % 60;

    const formattedMinutes = minutes.toString().padStart(2, '0');
    const formattedSeconds = remainingSeconds.toString().padStart(2, '0');

    return `${formattedMinutes}:${formattedSeconds}`;
}
