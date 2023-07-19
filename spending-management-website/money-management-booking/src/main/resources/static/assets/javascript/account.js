





function DeleteCategory(id){
    if(confirm("Confirm to Delete")){
        window.location="/settings/delete-category/" +id
    }
}


function confirmDelete(id){
    if(confirm("Confirm to Delete")){
        window.location="/users/delete-account/" +id
    }
}

function confirmDeleteExpense(id){
    if(confirm("Confirm to Delete")){
        window.location="/users/delete-expense/" +id
    }
}


function confirmDeleteIncome(id){
    if(confirm("Confirm to Delete")){
        window.location="/users/delete-income/" +id
    }
}


function changeStatusAccounts(currentStatus, accountId) {
    if (accountId != 0) {
        if (currentStatus === true) {
            if (confirm("are you sure to disable user: " + accountId)) {
                currentStatus = !currentStatus
                window.location="/users/list-account/"+accountId+'/'+currentStatus
            }
        } else {
            currentStatus = !currentStatus
            window.location="/users/list-account/"+accountId+'/'+currentStatus
        }
    }
}

// Lấy ngày hiện tại
var currentDate = new Date();

// Định dạng ngày thành chuỗi yyyy-MM-dd
var dateString = currentDate.toISOString().split('T')[0];

// Gán giá trị mặc định cho trường đầu vào
document.getElementById("myDate").value = dateString;


// Lấy tất cả các phần tử progress trong tài liệu HTML
var progressBars = document.querySelectorAll('progress');

// Lặp qua từng thanh progress và gắn sự kiện mouseover và mouseout
progressBars.forEach(function(progressBar) {
  progressBar.addEventListener('mouseover', function() {
    // Lấy giá trị từ thuộc tính value
    var value = parseFloat(progressBar.value);

    // Cập nhật giá trị để hiển thị khi chỉ chuột vào
    progressBar.setAttribute('value', value);
  });

  progressBar.addEventListener('mouseout', function() {
    // Đặt lại giá trị mặc định
    progressBar.removeAttribute('value');
  });
});




