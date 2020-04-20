function post() {
    var questionId = $("#question_id").val();
    var comment = $("#comment_count").val();
    if (!comment){
        return("不能回复空内容");
    }
    $.ajax({
        type:"POST",
        contentType:"application/json",
        url:"/comment",
        data:JSON.stringify({
            "parentId":questionId,
            "comment":comment,
            "type":1
        }),
        success:function(response){
            if (response.code == 200){
                window.location.reload();
            }else {
                if (response.code == 2003){
                    var b = confirm(response.message);
                    if (b){
                        window.open("/login");
                        /*window.localStorage.setItem("closable",true);*/
                    }
                }
            }
        },
        dataType:"json"
    });
}