function suppleUI(object) {

    var cls =  document.getElementsByClassName(object.selected);
    if (cls.length >0) {
        cls[0].setAttribute("class","selected");
    }

     var types = object.types;
    if (types!=null&&types.length >0){
        for (var i = 0; i < types.length; i++) {
            $("#home_ul").append(types[i]);
        }
    }


    $('ul.sf-menu').sooperfish();
}
