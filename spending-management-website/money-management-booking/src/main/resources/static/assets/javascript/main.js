
const numberInput = document.getElementById("numberInput");
const numberText = document.getElementById("numberText");
var datetimepicker = document.getElementById("datetimepicker");
var classifyMode = document.getElementById('classifyMode');
var PlusBtn = document.getElementsByName('AddNewDebtPlus');
var MinusBtn = document.getElementsByName('AddNewDebtMinus');
const now = new Date();
const formattedDate = now.getFullYear() + '-' + (now.getMonth() + 1).toString().padStart(2, '0') + '-' + now.getDate().toString().padStart(2, '0') + ' ' + now.getHours().toString().padStart(2, '0') + ':' + now.getMinutes().toString().padStart(2, '0');
var closeBtnAddNewDebt = document.getElementById('closeAddNewDebt');

flatpickr(datetimepicker, {
    enableTime: true,
    dateFormat: "Y-m-d H:i",
    time_24hr: true,
    defaultDate: new Date()
});
datetimepicker.value = formattedDate;
datetimepicker.placeholder = formattedDate;
closeBtnAddNewDebt.addEventListener('click', () => {
    datetimepicker.value = formattedDate;
    datetimepicker.placeholder = formattedDate;
});


$(function () {
    $("table").resizableColumns({
        store: window.store
    });
});

document.querySelectorAll('button[data-target="#new_debt"]').forEach(button => {
    button.addEventListener('click', () => {
        const tr = button.parentNode.parentNode.parentNode;
        const td1 = tr.querySelector('td:nth-child(1)').textContent;

        const form = document.getElementById('addNewDebtForm');
        const newDiv = document.getElementById('addNewDebtFormCustomerId');
        newDiv.textContent = td1;
        newDiv.value = td1;
        console.log(form);
    });
});

numberInput.addEventListener("input", (e) => {

    const value1 = e.target.value.replace(/,/g, ''); // Xóa các dấu ',' cũ
    const formattedValue = new Intl.NumberFormat('en-US').format(value1); // Định dạng lại số
    e.target.value = formattedValue; // Thêm dấu ',' vào số và gán lại giá trị cho input

    const inputValue = parseInt(value1);
    if (!isNaN(inputValue)) {
        if(inputValue != 0){
             numberText.innerText = numToWords(inputValue);
        } else {
             numberText.innerText = "";
        }
    } 
});

