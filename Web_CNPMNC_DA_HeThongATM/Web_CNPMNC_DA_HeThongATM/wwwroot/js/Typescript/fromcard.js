function GetdataValue(event) {
    var button = event.target;
    var data = button.getAttribute('data');
    console.log(data);
}
function nextPage(url) {
    location.href = url;
}
