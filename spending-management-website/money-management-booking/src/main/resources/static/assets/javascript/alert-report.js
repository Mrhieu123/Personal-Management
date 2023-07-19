

function report(error) {

    if (error != null && error != '') {
        let report = error.toString();
        let errors = report.split(',')

        if (!Array.isArray(errors)) {
            openPopup(error)
        } else {
            errors.forEach((item, index) => {
                if (index === 0) {
                    openPopup(item.trim());
                } else {
                    setTimeout(() => {
                        openPopup(item.trim());
                    }, 1000 * index);
                }
            });
        }
    }
}
var popupCount = 0.8;

function openPopup(text) {
    var newPopup = document.createElement("div");
    newPopup.className = "report_popup";
    newPopup.innerHTML = "<i class = 'fa-solid fa-bell' style = 'display:inline; margin-right:12px; font-size:20px'></i>" + "<p style='display:inline;'>" + text + "</p>";
    document.body.appendChild(newPopup);
    setTimeout(function () {
        newPopup.style.display = "block";
        newPopup.style.top = (popupCount * (newPopup.offsetHeight + 10)) + "px";
        newPopup.style.right = "0";
        popupCount++;

        setTimeout(function () {
            newPopup.classList.add("hide_report");
        }, 4000);
        setTimeout(function () {
            setTimeout(function () {
                newPopup.remove();
                popupCount--;
            }, 100);
        }, 4900);

        newPopup.addEventListener("click", function () {
            setTimeout(function () {
                newPopup.remove();
                popupCount--;
            }, 100);
        });
        newPopup.classList.add("show_report");


    }, 100);

}

