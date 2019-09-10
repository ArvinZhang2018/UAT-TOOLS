var toolsLanguage = '';

function smallAlertWindow(position, status, desc) {
    const Toast = Swal.mixin({
        toast: true,
        position: position,
        showConfirmButton: false,
        timer: 10000
    });
    Toast.fire({
        type: status,
        title: desc
    })
    initPage();

}

function smallAlertWindowSuccessful() {
    smallAlertWindow('top', 'success',
        'Congratulations, the operation is successful ●ω●');
}

function smallAlertWindowFailure() {
    smallAlertWindow('top', 'error',
        'Sorry, the operation failed, please try again ●＾●');
}

function smallAlertWindowWithMessage(message) {
    Swal.fire({
        type: message.alertWindowType,
        title: message.title,
        position:'top',
        customClass: 'customLayoutForDevice',
        html: message.html
    })
    initPage();
}

function doPost(url, params, doSuccess, doFailure) {
    $.ajax({
        type: "POST",
        url: url,
        contentType: 'application/json',
        data: JSON.stringify(params),
        success: function (response) {
            if (response) {
                if (response.success) {
                    doSuccess(response.result);
                } else {
                    let message = toolsLanguage === 'CN' ? response.messageCn
                        : response.messageEn;
                    smallAlertWindow('top', 'error',
                        'Sorry, the operation failed, please try again ●＾● . '
                        + response.retCode + '-' + response.errCode +
                        "-" + message);
                }
            }
        },
        error: function (response) {
            doFailure(response);
        }
    })
}

function doGet(url, doSuccess, doFailure) {
    $.ajax({
        method: "GET",
        url: url,
        success: function (response) {
            doSuccess(response);
        },
        error: function (response) {
            doFailure(response);
        }
    })
}

function chooseLanguage(language) {
    $("#settingsLanguageVal").val(language);
    $("#settingsLanguageBtn").html(language)
}

function changeToolsVersion(version) {
    $("#toolsVersion").val(version);
    $("#toolsVersionBtn").html("V" + version)
}

function showLog(logSwitch) {
    $("#settingsLogVal").val(logSwitch);
    if (logSwitch == '0') {
        $("#settingsLogBtn").html("ON")
    } else {
        $("#settingsLogBtn").html("OFF")
    }
}

function initPage() {
    doPost('/api/crm-x/getUserInfo/v1', {fingerprint: fingerprint},
        function (res) {
            let ops = res.userOperations;
            if (ops && ops.length > 0) {
                for (let i = 0; i < ops.length; i++) {
                    $("#operationClickCount" + ops[i].operation).html(
                        ops[i].clickCount);
                }
            }
            toolsLanguage = res.userInfo.language;
            if (res.userInfo.language === 'EN') {
                let opNameArr = $(".opName");
                let opDescArr = $(".opDesc");
                for (let i = 0; i < opNameArr.size(); i++) {
                    opNameArr.eq(i).html(opNameArr.eq(i).attr("opnameen"))
                    opDescArr.eq(i).html(opDescArr.eq(i).attr("opdescen"))
                }
            } else {
                let opNameArr = $(".opName");
                let opDescArr = $(".opDesc");
                for (let i = 0; i < opNameArr.size(); i++) {
                    opNameArr.eq(i).html(opNameArr.eq(i).attr("opnamecn"))
                    opDescArr.eq(i).html(opDescArr.eq(i).attr("opdesccn"))
                }
            }
            // let createDate = date('Y-m-d  H:i:s', '' + res.userInfo.createTime
            //     / 1000);
            fillLogArea(res.logs, res.userInfo);
        }, function () {
            smallAlertWindow('top', 'error',
                'Sorry , initializing the page failed !');
        })
}

function numberAddOne(operationId) {
    $("#operationClickCount" + operationId).html(parseInt(
        $("#operationClickCount" + operationId).html()) + 1)
    $("#userOperationClickCount" + operationId).html(parseInt(
        $("#userOperationClickCount" + operationId).html())
        + 1)
}

function recordClickEvent(operationId) {
    numberAddOne(operationId);
    doPost("/api/crm-x/recordClickEven/v1",
        {fingerprint: fingerprint, operation: operationId}
        , function () {
        }, function () {
            smallAlertWindowFailure();
        }
    )
}

function fillLogArea(logs, userInfo) {
    if (userInfo.showLog == '1') {
        $("#logArea").css("display", "none")
    } else {
        $("#logArea").css("display", "block")
    }

    $("#logList").empty();
    for (let i = 0; i < logs.length; i++) {
        let log = logs[i];
        let opName = log.opNameCn;
        let remark = log.remarkCn;
        let className = 'selfLog';
        let user = log.fingerprint;
        if (userInfo.language === 'EN') {
            opName = log.opNameEn;
            remark = log.remarkEn;
        }
        let createDate = date('Y-m-d  H:i:s', '' + log.createTime / 1000);
        if (log.fingerprint != fingerprint) {
            className = 'otherLog';
        }
        if (log.nickName) {
            user = log.nickName;
        }
        $("#logList").append(
            $("<li class='" + className + "'>[" + createDate + "] [" + opName
                + "] [" + user + "] " + remark + "</li>"))
    }
}

function saveSettings(params) {
    doPost("/api/crm-x/saveSettings/v1", params
        , function () {
            initPage();
            smallAlertWindow('top', 'success', 'Your work has been saved');
        }, function () {
            smallAlertWindowFailure();
        }
    )
}

function saveProfile(params) {
    doPost('/api/crm-x/saveProfile/v1', params, function () {
        initPage();
        smallAlertWindow('top', 'success', 'Your work has been saved');
    }, function () {
        smallAlertWindowFailure();
    })
}

function doOperation(operationId, executeMethod, configs) {
    recordClickEvent(operationId);
    if (!executeMethod || executeMethod == '' || executeMethod == 'null') {
        eval("stepProcess(" + operationId + "," + configs + ")");
    } else {
        eval(executeMethod + "(" + operationId + "," + configs + ")");
    }
}

function buildParams(operationId, propertyName, params, extraParams) {
    let finalParams = {
        operation: operationId,
        fingerprint: fingerprint
    };
    $.extend(finalParams, extraParams);
    for (let i = 0; i < propertyName.length; i++) {
        finalParams[propertyName[i]] = params[i];
    }
    return finalParams;
}

function getValidators(validatorsName, tip) {
    if (validatorsName === 'validate_is_empty') {
        return (value) => {
            return new Promise((resolve) => {
                if (value === '') {
                    resolve(tip)
                } else {
                    resolve()
                }
            })
        }
    } else if (validatorsName === 'validate_is_password') {
        return (value) => {
            return new Promise((resolve) => {
                if (value === '' || value.length < 4 || value.length > 30) {
                    resolve(tip)
                } else {
                    resolve()
                }
            })
        }

    } else if (validatorsName === 'validate_is_amount') {
        return (value) => {
            return new Promise((resolve) => {
                if (value === '' || value > 20 || value < 1) {
                    resolve(tip)
                } else {
                    resolve()
                }
            })
        }

    }

    return null;
}

function stepProcess(operationId, configs) {
    let config;
    if (toolsLanguage === 'CN') {
        config = configs.cn;
    } else {
        config = configs.en;
    }

    let queue = [];
    let progressSteps = [];
    for (let i = 0; i < config.queue.length; i++) {
        progressSteps.push(i + 1);
        let baseJson = {
            inputAutoTrim: true
        };
        baseJson["inputValidator"] = getValidators(configs.basic.validators[i],
            config.swal.tips[i])
        let finalJson = $.extend({}, baseJson, config.queue[i]);
        queue.push(finalJson);
    }
    Swal.mixin({
        input: config.swal.input,
        confirmButtonText: config.swal.confirmButtonText,
        showCancelButton: true,
        reverseButtons: true,
        padding: '2rem',
        position: 'top',
        customClass: 'customLayout',
        progressSteps: progressSteps
    }).queue(queue).then((result) => {
        if (result.value) {
            doPost(configs.basic.url,
                buildParams(operationId, configs.basic.args, result.value,
                    configs.basic.defaultArgs),
                function (result) {
                    if (result) {
                        smallAlertWindowWithMessage(result)
                    } else {
                        smallAlertWindowSuccessful();
                    }
                }, smallAlertWindowFailure
            );
        }
    })
}

function operation_100(operationId, configs) {
    let config;
    if (toolsLanguage === 'CN') {
        config = configs.cn;
    } else {
        config = configs.en;
    }
    doPost(configs.basic.url, {fingerprint: fingerprint},
        function (userInfo) {
            Swal.fire({
                title: config.swal.title,
                padding: config.swal.padding,
                position: config.swal.position,
                customClass: config.swal.customClass,
                html:
                '<form class="form-horizontal">'
                + '    <div class="form-group" style="margin-top:20px;">'
                + '        <label class="col-sm-4 control-label">'
                + config.template[0] + '</label>'
                + '    <input type="hidden" id="settingsLanguageVal" value="'
                + userInfo.language + '"/>'
                + '        <div class="col-sm-8">'
                + '            <div class="input-group-btn">'
                + '                <button type="button" id="settingsLanguageBtn" class="btn btn-default dropdown-toggle"'
                + '                        style="width:100%;" data-toggle="dropdown" aria-haspopup="true"'
                + '                        aria-expanded="false">'
                + '                    ' + userInfo.language + ''
                + '                </button>'
                + '                <ul class="dropdown-menu" style="width:100%;text-align:center!important;">'
                + '                    <li><a href="#" onclick="chooseLanguage(\'CN\')">CN</a></li>'
                + '                    <li><a href="#" onclick="chooseLanguage(\'EN\')">EN</a></li>'
                + '                </ul>'
                + '            </div>'
                + '        </div>'
                + '    </div>'
                + ''
                + '    <div class="form-group">'
                + '        <label class="col-sm-4 control-label">'
                + config.template[1] + '</label>'
                + '        <input type="hidden" id="settingsLogVal" value="'
                + userInfo.showLog + '"/>'
                + '        <div class="col-sm-8">'
                + '            <div class="input-group-btn">'
                + '                <button type="button" id="settingsLogBtn" class="btn btn-default dropdown-toggle"'
                + '                        style="width:100%;" data-toggle="dropdown" aria-haspopup="true"'
                + '                        aria-expanded="false">'
                + '                    ' + ((userInfo.showLog == '0') ? 'ON'
                    : 'OFF') + ''
                + '                </button>'
                + '                <ul class="dropdown-menu" style="width:100%;text-align:center!important;">'
                + '                    <li><a href="#" onclick="showLog(\'0\')">ON</a></li>'
                + '                    <li><a href="#" onclick="showLog(\'1\')">OFF</a></li>'
                + '                </ul>'
                + '            </div>'
                + '        </div>'
                + '    </div>'
                + '    <div class="form-group">'
                + '        <label class="col-sm-4 control-label">'
                + config.template[2] + '</label>'
                + '        <input type="hidden" id="toolsVersion" value="'
                + userInfo.version + '"/>'
                + '        <div class="col-sm-8">'
                + '            <div class="input-group-btn dropup">'
                + '                <button type="button" id="toolsVersionBtn" class="btn btn-default dropdown-toggle"'
                + '                        style="width:100%;" data-toggle="dropdown" aria-haspopup="true"'
                + '                        aria-expanded="false">'
                + '                    V' + userInfo.version + ''
                + '                </button>'
                + '                <ul class="dropdown-menu" style="width:100%;text-align:center!important;">'
                + '                    <li><a href="#" onclick="changeToolsVersion(\'1\')">V1</a></li>'
                + '                    <li><a href="#" onclick="changeToolsVersion(\'2\')">V2</a></li>'
                + '                </ul>'
                + '            </div>'
                + '        </div>'
                + '    </div>'
                + '</form>',
                focusConfirm: true,
                showCancelButton: true,
                reverseButtons: true,
                confirmButtonText: config.swal.confirmButtonText,
                preConfirm: () => {
                    return [$("#settingsLanguageVal").val(),
                        $("#settingsLogVal").val(), $("#toolsVersion").val()]
                }
            }).then((result) => {
                if (result.value) {
                    let finalParams = {
                        fingerprint: fingerprint,
                        operation: operationId
                    };
                    for (let i = 0; i < configs.basic.args.length; i++) {
                        finalParams[configs.basic.args[i]] = result.value[i];
                    }
                    saveSettings(finalParams)
                }
            })
        })
}

function operation_101(operationId, configs) {
    let config;
    if (toolsLanguage === 'CN') {
        config = configs.cn;
    } else {
        config = configs.en;
    }
    doPost(configs.basic.url, {fingerprint: fingerprint},
        function (userInfo) {
            Swal.fire({
                title: config.swal.title,
                padding: config.swal.padding,
                position: config.swal.position,
                customClass: config.swal.customClass,
                html:
                '<form class="form-horizontal">'
                + '    <div class="form-group" style="margin-top:20px;">'
                + '        <label for="profileFingerprint" class="col-sm-4 control-label">'
                + config.template[0] + '</label>'
                + '        <div class="col-sm-8">'
                + '            <input type="text"  readonly="true" class="form-control" value="'
                + userInfo.fingerprint + '" id="profileFingerprint"/>'
                + '        </div>'
                + '    </div>'
                + '    <div class="form-group">'
                + '        <label for="profileNickname" class="col-sm-4 control-label">'
                + config.template[1] + '</label>'
                + '        <div class="col-sm-8">'
                + '            <input type="text" class="form-control" value="'
                + userInfo.nickName
                + '" id="profileNickname" placeholder="Input your nickname"/>'
                + '        </div>'
                + '    </div>'
                + '    <div class="form-group">'
                + '        <label for="profileRegisterDate" class="col-sm-4 control-label">'
                + config.template[2] + '</label>'
                + '        <div class="col-sm-8">'
                + '            <input type="text"  readonly="true" value="'
                + date('Y-m-d  H:i:s', '' + userInfo.createTime / 1000)
                + '" class="form-control" id="profileRegisterDate" />'
                + '        </div>'
                + '    </div>'
                + '    <div class="form-group">'
                + '        <label for="profileScore" class="col-sm-4 control-label">'
                + config.template[3] + '</label>'
                + '        <div class="col-sm-8">'
                + '            <input type="text"  readonly="true" value="'
                + userInfo.score
                + '" class="form-control" id="profileScore" />'
                + '        </div>'
                + '    </div>'
                + '</form>',
                focusConfirm: true,
                showCancelButton: true,
                reverseButtons: true,
                confirmButtonText: config.swal.confirmButtonText,
                preConfirm: () => {
                    return [$("#profileNickname").val()]
                }
            }).then((result) => {
                if (result.value) {
                    let finalParams = {
                        fingerprint: fingerprint,
                        operation: operationId
                    };
                    for (let i = 0; i < configs.basic.args.length; i++) {
                        finalParams[configs.basic.args[i]] = result.value[i];
                    }
                    saveProfile(finalParams)
                }
            })
        })
}

function operation_12(operationId, configs) {
    let config;
    if (toolsLanguage === 'CN') {
        config = configs.cn;
    } else {
        config = configs.en;
    }
    doPost(configs.basic.url, {fingerprint: fingerprint},
        function (userInfo) {
            Swal.fire({
                title: config.swal.title,
                padding: config.swal.padding,
                position: config.swal.position,
                customClass: 'customLayoutForNotification',
                html:
                '<div>'
                + '    <div class="input-group">'
                + '        <div class="input-group-btn">'
                + '            <button type="button" id="notificationDesc" class="btn btn-default dropdown-toggle"'
                + '                    style="width:250px;" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">'
                + '                Normal origin purchase<span class="caret" style="margin-left:2px;"></span>'
                + '            </button>'
                + '            <ul class="dropdown-menu">'
                + '                <li><a href="#" class="fetchTemplate" onclick="fetchTemplate(1,this)">Normal origin'
                + '                    purchase</a></li>'
                + '                <li><a href="#" class="fetchTemplate" onclick="fetchTemplate(2,this)">Renewal origin'
                + '                    purchase</a></li>'
                + '                <li><a href="#" class="fetchTemplate" onclick="fetchTemplate(3,this)">Renewal subscription'
                + '                    purchase</a></li>'
                + '                <li role="separator" class="divider"></li>'
                + '                <li><a href="#" class="fetchTemplate" onclick="fetchTemplate(4,this)">Refund</a></li>'
                + '                <li role="separator" class="divider"></li>'
                + '                <li><a href="#" class="fetchTemplate" onclick="fetchTemplate(5,this)">Charge back</a></li>'
                + '                <li role="separator" class="divider"></li>'
                + '                <li><a href="#" class="fetchTemplate" onclick="fetchTemplate(6,this)">Rebilling'
                + '                    cancelled</a></li>'
                + '                <li><a href="#" class="fetchTemplate" onclick="fetchTemplate(7,this)">Rebilling cancelled by'
                + '                    charge back</a></li>'
                + '                <li><a href="#" class="fetchTemplate" onclick="fetchTemplate(8,this)">Rebilling cancelled by'
                + '                    notpossible</a></li>'
                + '                <li><a href="#" class="fetchTemplate" onclick="fetchTemplate(9,this)">Rebilling cancelled by'
                + '                    refund</a></li>'
                + '                <li><a href="#" class="fetchTemplate" onclick="fetchTemplate(10,this)">Rebilling cancelled'
                + '                    by requseted</a></li>'
                + '                <li><a href="#" class="fetchTemplate" onclick="fetchTemplate(11,this)">Rebilling cancelled'
                + '                    by TCA</a></li>'
                + '                <li><a href="#" class="fetchTemplate" onclick="fetchTemplate(12,this)">Rebilling cancelled'
                + '                    by TDL</a></li>'
                + ''
                + '            </ul>'
                + '        </div>'
                + '        <input type="hidden" id="templateIndex" value="1"/>'
                + '        <input type="number" class="form-control" placeholder="Invoice ID" id="invoiceId"/>'
                + '        <div class="input-group-btn">'
                + '            <a type="button" class="btn btn-default"'
                + '                    onclick="replaceInvoiceId()">Swap'
                + '            </a>'
                + '        </div>'
                + '    </div>'
                + '    <textarea id="notification" rows="35" cols="78" style="resize:none;font-size:13px;margin-top:20px;"></textarea>'
                + '</div>',
                focusConfirm: true,
                showCancelButton: true,
                reverseButtons: true,
                confirmButtonText: config.swal.confirmButtonText,
                preConfirm: () => {
                    return [$("#profileNickname").val()]
                }
            }).then((result) => {
                if (result.value) {
                    let finalParams = {
                        fingerprint: fingerprint,
                        operation: operationId
                    };
                    for (let i = 0; i < configs.basic.args.length; i++) {
                        finalParams[configs.basic.args[i]] = result.value[i];
                    }
                    doSendNotification();
                }
            })
        })
}

function operation_13(operationId, configs) {
    let config;
    if (toolsLanguage === 'CN') {
        config = configs.cn;
    } else {
        config = configs.en;
    }
    doPost(configs.basic.url, {fingerprint: fingerprint},
        function (userInfo) {
            Swal.fire({
                title: config.swal.title,
                padding: config.swal.padding,
                position: config.swal.position,
                customClass: 'customLayoutForNotification',
                html:'<div>'
                + '<div  style="float:right;margin-bottom:10px;">'
                + '<form class="form-inline">'
                + '    <div class="form-group">'
                + '        <label class="sr-only" for="invoiceIdForEditRenewal">originOrderIdForEditRenewal</label>'
                + '        <input type="number" class="form-control" id="invoiceIdForEditRenewal"'
                + '               placeholder="Invoice ID"/>'
                + '    </div>'
                + '    <div class="form-group">'
                + '        <label class="sr-only" for="originOrderIdForEditRenewal">originOrderIdForEditRenewal</label>'
                + '        <input type="number" class="form-control" id="originOrderIdForEditRenewal"'
                + '               placeholder="Origin order ID"/>'
                + '    </div>'
                + '    <a class="btn btn-default" onclick="return doSearchRenewal()">Search</a>'
                + '</form>'
                + '</div>'
                + '<div>'
                + '<form class="form-horizontal">'
                + '    <input type="hidden" id="renewalId"/>'
                + '    <div class="form-group">'
                + '        <label for="originOrderID" class="col-sm-3 control-label">OriginOrderID</label>'
                + '        <div class="col-sm-9">'
                + '            <input type="text" class="form-control" id="originOrderID" readonly="readonly" placeholder="Origin order ID"/>'
                + '        </div>'
                + '    </div>'
                + '    <div class="form-group">'
                + '        <label for="subscriptionId" class="col-sm-3 control-label">SubscriptionId</label>'
                + '        <div class="col-sm-9">'
                + '            <input type="text" class="form-control" id="subscriptionId" placeholder="Subscription ID"/>'
                + '        </div>'
                + '    </div>'
                + '    <div class="form-group">'
                + '        <label for="nextPaymentDate" class="col-sm-3 control-label">NextPaymentDate</label>'
                + '        <div class="col-sm-9">'
                + '            <input type="text" class="form-control" id="nextPaymentDate"'
                + '                   placeholder="Next payment date"/>'
                + '        </div>'
                + '    </div>'
                + '    <div class="form-group">'
                + '        <label for="invoiceExpireDate" class="col-sm-3 control-label">InvoiceExpireDate</label>'
                + '        <div class="col-sm-9">'
                + '            <input type="text" class="form-control" id="invoiceExpireDate"'
                + '                   placeholder="Invoice expire date"/>'
                + '        </div>'
                + '    </div>'
                + '    <div class="form-group">'
                + '        <label for="renewalStatus" class="col-sm-3 control-label">RenewalStatus</label>'
                + '        <div class="col-sm-9">'
                + '            <input type="number" class="form-control" id="renewalStatus" placeholder="Renewal status"/>'
                + '        </div>'
                + '    </div>'
                + '</form>'
                + '</div>'
                + '</div>',
                focusConfirm: true,
                showCancelButton: true,
                reverseButtons: true,
                confirmButtonText: config.swal.confirmButtonText,
                preConfirm: () => {
                    return [$("#profileNickname").val()]
                }
            }).then((result) => {
                if (result.value) {
                    let finalParams = {
                        fingerprint: fingerprint,
                        operation: operationId
                    };
                    for (let i = 0; i < configs.basic.args.length; i++) {
                        finalParams[configs.basic.args[i]] = result.value[i];
                    }
                    doEditRenewal();
                }
            })
        })
}


function formatJson(txt, compress/*是否为压缩模式*/) {/* 格式化JSON源码(对象转换为JSON文本) */
    var indentChar = '    ';
    if (/^\s*$/.test(txt)) {
        alert('数据为空,无法格式化! ');
        return;
    }
    try {
        var data = eval('(' + txt + ')');
    }
    catch (e) {
        alert('数据源语法错误,格式化失败! 错误信息: ' + e.description, 'err');
        return;
    }
    ;
    var draw = [], last = false, This = this, line = compress ? '' : '',
        nodeCount = 0, maxDepth = 0;

    var notify = function (name, value, isLast, indent/*缩进*/, formObj) {
        nodeCount++;
        /*节点计数*/
        for (var i = 0, tab = ''; i < indent; i++) {
            tab += indentChar;
        }
        /* 缩进HTML */
        tab = compress ? '' : tab;
        /*压缩模式忽略缩进*/
        maxDepth = ++indent;
        /*缩进递增并记录*/
        if (value && value.constructor == Array) {/*处理数组*/
            draw.push(tab + (formObj ? ('"' + name + '":') : '') + '[' + line);
            /*缩进'[' 然后换行*/
            for (var i = 0; i < value.length; i++) {
                notify(i, value[i], i == value.length - 1, indent, false);
            }
            draw.push(tab + ']' + (isLast ? line : (',' + line)));
            /*缩进']'换行,若非尾元素则添加逗号*/
        } else if (value && typeof value == 'object') {/*处理对象*/
            draw.push(tab + (formObj ? ('"' + name + '":') : '') + '{' + line);
            /*缩进'{' 然后换行*/
            var len = 0, i = 0;
            for (var key in value) {
                len++;
            }
            for (var key in value) {
                notify(key, value[key], ++i == len, indent,
                    true);
            }
            draw.push(tab + '}' + (isLast ? line : (',' + line)));
            /*缩进'}'换行,若非尾元素则添加逗号*/
        } else {
            if (typeof value == 'string') {
                value = '"' + value + '"';
            }
            draw.push(tab + (formObj ? ('"' + name + '":') : '') + value
                + (isLast ? '' : ',') + line);
        }
        ;
    };
    var isLast = true, indent = 0;
    notify('', data, isLast, indent, false);
    return draw.join('');
}