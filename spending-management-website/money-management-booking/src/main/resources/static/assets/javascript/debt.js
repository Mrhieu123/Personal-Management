$(document).ready(function () {
    $(".resizable-column").resizable({
        handles: 'e', // Chỉ cho phép kéo dãn chiều rộng từ phía bên phải
        minWidth: 50, // Chiều rộng tối thiểu của cột
        resize: function (event, ui) {
            // Khi cột được kéo dãn, điều chỉnh chiều rộng của các ô dữ liệu tương ứng
            var columnIndex = $(this).index();
            $("table tbody tr").each(function () {
                $(this).find("td:eq(" + columnIndex + ")").width(ui.size.width);
            });
        }
    });
});

function selectOptions(isPlus) {
    var plusOption = document.getElementById("plus");
    var minusOption = document.getElementById("minus");
    var classifyValue = document.getElementById("classifyValue");

    if (isPlus) {
        plusOption.classList.add("active");
        minusOption.classList.remove("active");
        classifyValue.value = "true";
    } else {
        plusOption.classList.remove("active");
        minusOption.classList.add("active");
        classifyValue.value = "false";
    }
}

function selectOption(isPlus, id) {
    var plusOption = document.getElementById("plus" + id);
    var minusOption = document.getElementById("minus" + id);
    var classifyValue = document.getElementById("classifyValue" + id);

    if (isPlus) {
        plusOption.classList.add("active");
        minusOption.classList.remove("active");
        classifyValue.value = "true";
    } else {
        plusOption.classList.remove("active");
        minusOption.classList.add("active");
        classifyValue.value = "false";
    }
}


document.getElementById("filterType").onchange = function () {
    var filterType = this.value;
    var rangeContainer = document.getElementById("rangeContainer");
    var filterValueStart = document.getElementById("filterValueStart");
    var filterValueEnd = document.getElementById("filterValueEnd");
    var errorSpan = document.getElementById("errorSpan");
    var searchButton = document.getElementById("searchButton");

    if (filterType === "total") {
        filterValueStart.type = "number";
        filterValueEnd.type = "number";
        filterValueStart.classList.remove("error");
        filterValueEnd.classList.remove("error");
        errorSpan.textContent = "";
        errorSpan.style.display = "none";
    } else if (filterType === "date") {
        filterValueStart.type = "date";
        filterValueEnd.type = "date";
        errorSpan.style.display = "none";
    }

    filterValueStart.oninput = function () {
        var startValue = filterValueStart.value;
        var endValue = filterValueEnd.value;

        if (filterType === "total" && parseFloat(startValue) > parseFloat(endValue)) {
            errorSpan.textContent = "Start value must be less than end value";
            errorSpan.style.display = "block";
            filterValueStart.classList.add("error");
            filterValueEnd.classList.add("error");
        } else if (filterType === "date" && startValue > endValue) {
            errorSpan.textContent = "Start date must be before end date";
            errorSpan.style.display = "block";
            filterValueStart.classList.add("error");
            filterValueEnd.classList.add("error");
        } else {
            errorSpan.textContent = "";
            errorSpan.style.display = "none";
            filterValueStart.classList.remove("error");
            filterValueEnd.classList.remove("error");
        }
        toggleSearchButtonState();
    };

    filterValueEnd.oninput = function () {
        var startValue = filterValueStart.value;
        var endValue = filterValueEnd.value;

        if (filterType === "total" && parseFloat(startValue) > parseFloat(endValue)) {
            errorSpan.textContent = "Start value must be less than end value";
            errorSpan.style.display = "block";
            filterValueStart.classList.add("error");
            filterValueEnd.classList.add("error");
        } else if (filterType === "date" && startValue > endValue) {
            errorSpan.textContent = "Start date must be before end date";
            errorSpan.style.display = "block";
            filterValueStart.classList.add("error");
            filterValueEnd.classList.add("error");
        } else {
            errorSpan.textContent = "";
            errorSpan.style.display = "none";
            filterValueStart.classList.remove("error");
            filterValueEnd.classList.remove("error");
        }
    };

    // function toggleSearchButtonState() {
    //     var startValue = filterValueStart.value.trim();
    //     var endValue = filterValueEnd.value.trim();

    //     if (startValue === "" || endValue === "") {
    //         searchButton.disabled = true;
    //     } else {
    //         searchButton.disabled = false;
    //     }
    // }

    toggleSearchButtonState();
};

document.getElementById("filterForm").onsubmit = function (event) {
    var startValue = document.getElementById("filterValueStart").value.trim();
    var endValue = document.getElementById("filterValueEnd").value.trim();

    if ((startValue != "" && endValue === "") || (startValue === "" && endValue != "")) {
        event.preventDefault();
        alert("Please enter both From and To values before submitting the form.");
    }
};

document.addEventListener('DOMContentLoaded', function () {
    var clearButton = document.getElementById('clearButton');
    clearButton.addEventListener('click', function () {
        // Xử lý sự kiện khi người dùng nhấp vào nút Clear Filter
        document.getElementById('filterForm').reset();
        document.getElementById('rangeContainer').classList.remove('active'); // Bỏ chọn active class cho rangeContainer (nếu có)
        document.getElementById("filterValueStart").value = "";
        document.getElementById("filterValueEnd").value = "";
        var debtorId = document.getElementById("debtorId").value;
        // window.location.href = '/Debtor/Detail/view-detail/' + debtorId;

    });
});
