
function GetdataValue(event: Event) {
    let button = event.target as HTMLButtonElement;
    let data = button.getAttribute('data');
    console.log(data);
    }

function nextPage(url: string) {
   
    window.location.href = url; 
}

