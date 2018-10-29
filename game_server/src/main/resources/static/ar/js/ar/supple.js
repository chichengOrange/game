function suppleUI(object) {


    /*首页*/
    const types = object.types;
    if (types != null && types.length > 0) {
        for (var i = 0; i < types.length; i++) {
            $("#home_ul").append(types[i]);
        }
    }


    /*contact UI*/
    const sessionUser = object.user;
    var contact;
    if (sessionUser == null) {
        contact = "<li id='contact'><a href='#'>登录</a> " +
            "<ul> " +
            "<li><a href='contact?show=showLogin'>登录</a></li> " +
            "<li><a href='contact?show=register'>注册</a></li> " +
            "</ul> " +
            "</li>";
    } else {

        if (sessionUser.rnameStatus == null || sessionUser.rnameStatus != 1) {
            contact = "<li id='contact'><a href='#'>" + sessionUser.username + "</a> " +
                "<ul> " +
                "<li><a href='contact?show=realName'>实名认证</a></li> " +
                "<li><a href='gameReplay'>记录</a></li> " +
                "<li><a href='logout'>注销</a></li> " +
                "</ul> " +
                "</li>";
        } else {
            contact = "<li id='contact'><a href='#'>" + sessionUser.username + "</a> " +
                "<ul> " +
                "<li><a href='gameReplay'>记录</a></li> " +
                "<li><a href='logout'>注销</a></li> " +
                "</ul> " +
                "</li>";
        }

    }

    $("#downloadC").before(contact);
    /*选中*/
    const cls = document.getElementsByClassName(object.selected);
    if (cls.length > 0) {
        cls[0].setAttribute("class", "selected");
    }

    $('ul.sf-menu').sooperfish();


    //禁止网页一些操作
    ban()

}




function ban() {
   /* document.oncontextmenu = function () {
        //alert('请不要点击鼠标右键！');
        return true;
    }*/

    document.onselectstart = function () {
        //onselectstart="return false" 禁止选中网页上的内容
        return false;
    };

   /* document.oncopy = function () {
        //oncopy="return false" 防复制用户在网页上选中的内容
        return true;
    };*/
}