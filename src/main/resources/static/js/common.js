var header = $("meta[name='_csrf_header']").attr("content");
var token = $("meta[name='_csrf']").attr("content");
function findComment() {
    $.ajax({
        type:'post',
        url:'/index/findComment',
        dataType:'json',
        // beforeSend: function (xhr) {
        //     xhr.setRequestHeader(header, token);
        // },
        success:function (data){
            $('#findComment').empty();
            var comments=data.payload.data2;
            $.each(comments, function (i){
                $('#findComment').append("<div href=\"#\" class=\"dropdown-item\">\n" +
                    "                                <div class=\"d-flex align-items-center\">\n" +
                    "                                    <img id=\"head_portrait\" class=\"rounded-circle\" src="+comments[i].head_portrait+"" +
                    " alt=\"\" style=\"width: 40px; height: 40px;\">\n" +
                    "                                    <div class=\"ms-2\">\n" +
                    "                                        <h6 class=\"fw-normal mb-0\">"+comments[i].name+" 发送一条评论留言" +
                    "</h6>\n" +
                    "                                        <small>"+comments[i].timeDifference+"留言" +
                    "</small>\n" +
                    "                                    </div>\n" +
                    "                                </div>\n" +
                    "                            </div>"+"" +
                    "<hr class=\"dropdown-divider\">")
            })
            $('#findComment').append("<a onclick=\"toFindComment()\" style='cursor: pointer' class=\"dropdown-item text-center\">查看全部评论留言</a>")
        },
        error: function () {
            alert("Ajax连接失败")
        }
    })
}
function toFindComment(){
    $.ajax({
        success: function (){
            window.location.href='/index/laundry_service?#dingWei';
        }
    })
}

function findOrder(){
    $.ajax({
        type: "post",
        dataType: "json",
        url: "/index/findOrder",
        // beforeSend: function (xhr) {
        //     xhr.setRequestHeader(header, token);
        // },
        success: function (data){
            $("#findOrder").empty();
            var orders=data.payload.data;
            $.each(orders,function (i){
                $("#findOrder").append(" <div href=\"#\" class=\"dropdown-item\">\n" +
                    "                                <h6 class=\"fw-normal mb-0\">"+orders[i].name+" 的新订单" +
                    "</h6>\n" +
                    "                                <small>"+orders[i].timeDifference+"下单" +
                    "</small>\n" +
                    "                            </div>\n" +
                    "                            <hr class=\"dropdown-divider\">"+"" )
            })
            $("#findOrder").append("<a onclick=\"toFindOrder()\" style=\"cursor: pointer\" class=\"dropdown-item text-center\">查看全部订单信息</a>")
        }
    })
}
function toFindOrder(){
    $.ajax({
        success:function (){
            window.location.href="/admin/adminOrder"
        }
    })
}