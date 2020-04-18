function post() {
    var questionId = $("#question_id").val();
    var comment = $("#comment_count").val();
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
                $("comment_section").hide();
            }else {
                alert(response.message);
            }
            console.log(response);
        },
        dataType:"json"
    });
    console.log(questionId);
    console.log(comment);
}