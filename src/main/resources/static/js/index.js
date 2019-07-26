layui.use(['element','form','layer','upload'],function () {
    var $ = layui.jquery;
    var element = layui.element;//加载element模块
    var form = layui.form;
    var layer = layui.layer;
    var upload = layui.upload;
    /*漂浮消息*/
    $.fn.Messager = function (result) {
        if(result.code==200){
            layer.msg(result.msg,{offset: '15px', time: 3000, icon: 1});
            setTimeout(function(){
                if(result.data=='submit[refresh]'){
                    parent.location.reload();
                    return;
                }
                if(result.data==null){
                    window.location.reload();
                }
                else {
                    window.location.href = result.data
                }
            },2000);
        }else {
            layer.msg(result.msg, {offset: '15px', time: 3000, icon: 2});
        }
    }
});
