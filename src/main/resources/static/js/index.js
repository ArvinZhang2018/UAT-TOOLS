var fingerprint = new Fingerprint().get();
function setDevicePlan() {
    var did = prompt("请输入 设备号（DID) :");
    if (!did) {
        return false;
    }
    var plan = prompt(
        "请输入 套餐名称 \n Free trial 请输入 0 \n One year plan 请输入 1 \n Two year plan 请输入 2");
    if (!plan) {
        return false;
    }
    console.log(did + "-" + plan);
    $.ajax({
        method: "POST",
        url: "/api/crm-x/initDeviceInfo/v1",
        data: {
            did: did,
            plan: plan
        },
        success: function (res) {
            alert(res);
        },
        error: function (res) {
            alert("Server error!");
        }
    })
}

function deleteDevicePlan() {
    var did = prompt("请输入 设备号（DID) :");
    if (!did) {
        return false;
    }
    $.ajax({
        method: "POST",
        url: "/api/crm-x/deleteDeviceInfo/v1",
        data: {
            did: did
        },
        success: function (res) {
            alert(res);
        },
        error: function (res) {
            alert("Server error!");
        }
    })
}

function clearDevice() {
    var did = prompt("请输入 设备号（DID , 确保该设备登录过) :");
    if (!did) {
        return false;
    }
    var days = prompt("请输入 剩余有效期的天数 (0 代表过期）");
    if (!days) {
        return false;
    }
    console.log(did + "-" + days);
    $.ajax({
        method: "POST",
        url: "/api/crm-x/clearServiceTime/v1",
        data: {
            type: 0,
            did: did,
            days: days
        },
        success: function (res) {
            alert(res);
        },
        error: function (res) {
            alert("Server error !");
        }
    })
}

function clearEmailAccount() {
    var did = prompt("请输入 账号（Email , 确保该Email账号登录并激活过) :");
    if (did.search('@') == -1 || did.search(".com") == -1) {
        alert("请输入有效的Email账号！")
        return false;
    }
    if (did.length < 6) {
        alert("Email 的长度不能小于6位！")
        return false;
    }
    var days = prompt("请输入 剩余有效期的天数 (0 代表过期）");
    if (!days) {
        return false;
    }
    console.log(did + "-" + days);
    $.ajax({
        method: "POST",
        url: "/api/crm-x/clearServiceTime/v1",
        data: {
            type: 1,
            did: did,
            days: days
        },
        success: function (res) {
            alert(res);
        },
        error: function (res) {
            alert("Server error !");
        }
    })
}

function clearAccountCard() {
    var did = prompt("请输入 预付费账号（账号卡 , 确保该账号卡登录并激活过) :");
    if (!did) {
        return false;
    }
    var days = prompt("请输入 剩余有效期的天数 (0 代表过期）");
    if (!days) {
        return false;
    }
    console.log(did + "-" + days);
    $.ajax({
        method: "POST",
        url: "/api/crm-x/clearServiceTime/v1",
        data: {
            type: 1,
            did: did,
            days: days
        },
        success: function (res) {
            alert(res);
        },
        error: function (res) {
            alert("Server error !");
        }
    })
}

function activeEmailAccount() {
    var email = prompt("请输入 账号（Email) 该操作将会激活该账号 :");
    if (!email) {
        return false;
    }
    if (email.search('@') == -1 || email.search(".com") == -1) {
        alert("请输入有效的Email账号！")
        return false;
    }
    if (email.length < 6) {
        alert("Email 的长度不能小于6位！")
        return false;
    }
    $.ajax({
        method: "POST",
        url: "/api/crm-x/activeEmailAccount/v1",
        data: {
            email: email
        },
        success: function (res) {
            alert(res);
        },
        error: function (res) {
            alert("Server error !");
        }
    })
}

function createEmailAccount() {
    var email = prompt("请输入 账号（Email) :");
    if (!email) {
        return false;
    }
    if (email.search('@') == -1 || email.search(".com") == -1) {
        alert("请输入有效的Email账号！");
        return false;
    }
    if (email.length < 6) {
        alert("Email 的长度不能小于6位！");
        return false;
    }
    var password = prompt("请输入 密码 :");
    if (!password) {
        return false;
    }
    if (password.length < 4) {
        alert("密码 的长度不能小于4位！");
        return false;
    }
    var name = prompt("请输入 昵称 :");
    if (!name) {
        return false;
    }
    $.ajax({
        method: "POST",
        url: "/api/crm-x/createEmailAccount/v1",
        data: {
            email: email,
            password: password,
            name: name
        },
        success: function (res) {
            alert(res);
        },
        error: function (res) {
            alert("Server error !");
        }
    })
}

function createAccountCard() {
    $.ajax({
        method: "POST",
        url: "/api/crm-x/createAccountCard/v1",
        success: function (res) {
            alert(res);
        },
        error: function (res) {
            alert("Server error !");
        }
    })
}

function getDelays() {
    var apiList = prompt("请输入 要分析的API :");
    if (!apiList) {
        return false;
    }
    $.ajax({
        method: "POST",
        url: "/api/crm-x/getDelays/v1",
        data: {
            delayApis: apiList.split(",")
        },
        success: function (res) {
            alert(res);
        },
        error: function (res) {
            alert("Server error !");
        }
    })
}

function sendNotification() {
    $('#myModal').modal('toggle')
    fetchTemplate($("#templateIndex").val(),
        $(".fetchTemplate").eq($("#templateIndex").val() - 1));
}

function doSendNotification() {
    $.ajax({
        method: "POST",
        url: "/api/crm-x/bill/sendNotification/v1",
        data: {
            notification: $("#notification").val()
        },
        success: function (res) {
            $('#myModal').modal('hide');
            showResult(res);
        },
        error: function (res) {
            $('#myModal').modal('hide');
            showResult("Server error!");
        }
    })
}

function showResult(result) {
    $("#operationResultContent").html(result);
    $('#operationResult').modal('show');
}

function fetchTemplate(index, obj) {
    $("#templateIndex").val(index);
    var invoiceId = $("#invoiceId").val();
    $("#notificationDesc").html($(obj).html()
        + " <span class=\"caret\" style=\"margin-left:20px;\">");
    $.ajax({
        method: "POST",
        url: "/api/crm-x/bill/getTemplate/v1?templateIndex=" + index
        + "&invoiceId=" + invoiceId,
        success: function (res) {
            $("#notification").val(res);
        },
        error: function (res) {
            $('#myModal').modal('hide');
            showResult("Server error!");
        }
    })
}

function replaceInvoiceId() {
    var invoiceId = $("#invoiceId").val();
    var templateIndex = $("#templateIndex").val();
    console.log(invoiceId+":"+templateIndex)
    if (invoiceId == '') {
        return false;
    }
    $.ajax({
        method: "POST",
        url: "/api/crm-x/bill/getInvoiceIdAndSign/v1?invoiceId=" + invoiceId +
        "&templateIndex=" + templateIndex,
        success: function (res) {
            $("#notification").val(res);
        },
        error: function (res) {
            $('#myModal').modal('hide');
            showResult("Server error!");
        }
    })
}

function editRenewal() {
    $('#editRenewal').modal('toggle')
}

function doSearchRenewal() {
    var invoiceIdForEditRenewal = $("#invoiceIdForEditRenewal").val();
    var originOrderIdForEditRenewal = $("#originOrderIdForEditRenewal").val();
    if (invoiceIdForEditRenewal == '' && originOrderIdForEditRenewal == '') {
        return false;
    }
    $.ajax({
        method: "POST",
        url: "/api/crm-x/bill/getRenewalInfo/v1?invoiceIdForEditRenewal="
        + invoiceIdForEditRenewal +
        "&originOrderIdForEditRenewal=" + originOrderIdForEditRenewal,
        success: function (res) {
            $("#subscriptionId").val(res.order_id)
            $("#originOrderID").val(res.origin_order_id)
            $("#nextPaymentDate").val(res.next_payment_date)
            $("#invoiceExpireDate").val(res.invoice_expire_date)
            $("#renewalStatus").val(res.status)
            $("#renewalId").val(res.id)
        },
        error: function (res) {
            showResult("Server error!");
        }
    })
    return false;
}

function doEditRenewal() {
    var renewalId = $("#renewalId").val();
    var originOrderID = $("#originOrderID").val();
    var subscriptionId = $("#subscriptionId").val();
    var nextPaymentDate = $("#nextPaymentDate").val();
    var invoiceExpireDate = $("#invoiceExpireDate").val();
    var renewalStatus = $("#renewalStatus").val();
    if (originOrderID == '' || renewalId == '' || subscriptionId == '') {
        return false;
    }
    $.ajax({
        method: "POST",
        url: "/api/crm-x/bill/editRenewalInfo/v1?originOrderID=" + originOrderID+
        "&subscriptionId=" + subscriptionId +
        "&nextPaymentDate=" + nextPaymentDate +
        "&invoiceExpireDate=" + invoiceExpireDate +
        "&renewalId=" + renewalId +
        "&renewalStatus=" + renewalStatus,
        success: function (res) {
            showResult(res);
        },
        error: function (res) {
            showResult("Server error!");
        }
    })
}

function changeToV2() {
    $.ajax({
        method: "POST",
        url: "/api/crm-x/changeVersion/v1",
        data: {
            fingerprint: fingerprint,
            version: "2"
        },
        success: function (res) {
            Swal.fire({
                position: 'top-end',
                type: 'success',
                title: 'Your work has been saved',
                showConfirmButton: false,
                timer: 1500
            })
        },
        error: function (res) {
            showResult("Server error!");
        }
    })
}

/*******************************************************/


