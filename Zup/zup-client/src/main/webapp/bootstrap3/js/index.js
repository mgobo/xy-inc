/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var width = null;
var body  = jQuery("body");
var div   = jQuery("div");

function sizeScreen() {
    width = window.innerWidth;
}

function ajaxStart(){
    body.addClass('loading');
}
function ajaxStop(){
    body.removeClass('loading');
}

function openModal(id) {
    if (id !== undefined) {
        jQuery(".modal-wide").on("show.bs.modal", function() {
            var height = jQuery(window).height() - 200;
            jQuery(this).find(".modal-body").css("max-height", height);
        });
        jQuery("#" + id).removeClass("modal hide fade");
        jQuery("#" + id).addClass("modal show fade");
        jQuery("#" + id).modal({
            "backdrop": "static",
            "keyboard": true,
            "show": true
        });
    }
}

function closeModal(id) {
    if (id !== undefined) {
        jQuery('#' + id).modal('hide');
        jQuery("#" + id).removeClass("modal show fade");
        jQuery("#" + id).addClass("modal hide fade");
    }
}
