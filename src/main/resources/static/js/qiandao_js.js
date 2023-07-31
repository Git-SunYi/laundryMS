var header = $("meta[name='_csrf_header']").attr("content");
var token = $("meta[name='_csrf']").attr("content");
$(function() {
    var signFun = function() {
        var dateArray=[];
        // var dateArray = [1, 2, 4, 6] // 假设已经签到的

        var $dateBox = $("#js-qiandao-list"),
            $currentDate = $(".current-date"),
            $qiandaoBnt = $("#js-just-qiandao"),
            _html = '',
            _handle = true,
            myDate = new Date();
        $currentDate.text(myDate.getFullYear() + '年' + parseInt(myDate.getMonth() + 1) + '月' + myDate.getDate() + '日');

        var monthFirst = new Date(myDate.getFullYear(), parseInt(myDate.getMonth()), 1).getDay();

        var d = new Date(myDate.getFullYear(), parseInt(myDate.getMonth() + 1), 0);
        var totalDay = d.getDate(); //获取当前月的天数

        for (var i = 0; i < 42; i++) {
            _html += ' <li><div class="qiandao-icon"></div></li>'
        }
        $dateBox.html(_html) //生成日历网格

        var $dateLi = $dateBox.find("li");

        $.ajax({
            url: '/index/findCheckInDate',
            dataType: 'json',
            type: 'post',
            // beforeSend: function (xhr) {
            //     xhr.setRequestHeader(header, token);
            // },
            success: function (data) {
                if (data.payload.days != null){
                    // $.each(data.payload.days, function (i) {
                    //     dateArray.push(data.payload.days[i]);
                    // })
                    dateArray=data.payload.days;
                }

                for (var i = 0; i < totalDay; i++) {
                    $dateLi.eq(i + monthFirst).addClass("date" + parseInt(i + 1));
                    for (var j = 0; j < dateArray.length; j++) {
                        if (i == dateArray[j]) {
                            $dateLi.eq(i - 1 + monthFirst).addClass("qiandao");
                        }
                    }
                } //生成当月的日历且含已签到

                $('#checkIn3').append(data.payload.continuousDays)
                $('#checkIn4').append(data.payload.currentMonthDays)
                $('#checkIn5').append(data.payload.totalDays)
                if (data.payload.totalReward==null){
                    $('#checkIn6').append("￥"+0)
                }else {
                    $('#checkIn6').append("￥"+data.payload.totalReward)
                }

                for (var j = dateArray.length-1; j >= 0; j--) {
                    $('#checkIn7').append("<tr><td>"+data.payload.checkInDates[j]+"</td>"+"" +
                        "<td>￥"+data.payload.rewards[j]+"</td>"+"" +
                        "<td>本月连续签到第"+data.payload.continuousDaysLists[j]+"天奖励</td></tr>")
                }

            }
        })

        $.ajax({
            url: '/index/findTodayReward',
            type: 'post',
            dataType: 'json',
            // beforeSend: function (xhr) {
            //     xhr.setRequestHeader(header, token);
            // },
            success:function (data){
                if (data.payload.todayReward != null){
                    $('.qiandao-notic').append("今日已领<span>"+data.payload.todayReward+"</span>元，请明日继续签到")
                }
            }
        })

        $.ajax({
            url: '/index/findChekInAdmin',
            type: 'post',
            dataType: 'json',
            // beforeSend: function (xhr) {
            //     xhr.setRequestHeader(header, token);
            // },
            success: function (data){
                $('#checkInAdmin').append(" <h4 style=\"color: #8dabd7;\">签到规则</h4>\n" +
                    "                                                    <p>首次签到获得"+data.payload.firstReward+"" +
                    "元现金奖励</p>\n" +
                    "                                                    <p>连续签到每天增加"+data.payload.rewardAdd+"" +
                    "元现金奖励</p>\n" +
                    "                                                    <p>连续签到"+data.payload.continuousDays+"" +
                    "天及以上每天获得"+data.payload.continuousReward+"元现金奖励</p>")
            }
        })

        $(".date" + myDate.getDate()).addClass('able-qiandao');

        $dateBox.on("click", "li", function () {
            if ($(this).hasClass('able-qiandao') && _handle) {
                $(this).addClass('qiandao');
                qiandaoFun();
            }
        }) //签到

        $qiandaoBnt.on("click", function () {
            if (_handle) {
                qiandaoFun();
            }
        }); //签到

        function qiandaoFun() {
            $.ajax({
                url: '/index/addCheckInDay',
                contentType: 'application/json;charset=utf-8',
                type: 'post',
                data: JSON.stringify({
                    checkInDate:new Date(),
                    // phone:phone
                }),
                // beforeSend: function (xhr) {
                //     xhr.setRequestHeader(header, token);
                // },
                success:function (data){
                    if (data.msg != null){
                        window.alert(data.msg)
                    }
                    if (data.msg == null) {
                        $qiandaoBnt.addClass('actived');
                        openLayer("qiandao-active", qianDao);
                        _handle = false;
                    }
                }
            })

        }

        function qianDao() {
            $.ajax({
                url: '/index/findTodayReward',
                type: 'post',
                dataType: 'json',
                // beforeSend: function (xhr) {
                //     xhr.setRequestHeader(header, token);
                // },
                success:function (data){
                    $('#checkIn1').append("<div class=\"yiqiandao-icon qiandao-sprits\"></div>您本月已连续签到<span>"+
                        data.payload.continuousDays+"</span>天");
                    $('#checkIn2').append("<span class=\"qiandao-jiangli-num\">"+data.payload.todayReward+"" +
                        "<em>元</em></span>");
                }
            })
            $(".date" + myDate.getDate()).addClass('qiandao');
        }

    }();

    function openLayer(a, Fun) {
        $('.' + a).fadeIn(Fun)
    } //打开弹窗

    var closeLayer = function() {
            $("body").on("click", ".close-qiandao-layer", function() {
                $(this).parents(".qiandao-layer").fadeOut()
                window.location.reload();
            })
        }() //关闭弹窗

    $("#js-qiandao-history").on("click", function() {
        openLayer("qiandao-history-layer", myFun);

        function myFun() {
            console.log(1)
        } //打开弹窗返回函数
    })

})
