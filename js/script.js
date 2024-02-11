// 上に戻るボタン
$(function () {
  var pagetop = $("#page_top");
  // ボタン非表示
  pagetop.hide();
  // 100px スクロールしたらボタン表示
  $(window).scroll(function () {
    if ($(this).scrollTop() > 100) {
      pagetop.fadeIn();
    } else {
      pagetop.fadeOut();
    }
  });
  pagetop.click(function () {
    $("body, html").animate({ scrollTop: 0 }, 500);
    return false;
  });
});

var css = GetCookie("CSS");
if (css == "") {
  css = "css/style3.css";
}
document.write('<LINK REL="stylesheet" HREF="' + css + '" TYPE="text/css">');

function SetCss(file) {
  SetCookie("CSS", file);
  window.location.reload();
}
function GetCookie(key) {
  var tmp = document.cookie + ";";
  var tmp1 = tmp.indexOf(key, 0);
  if (tmp1 != -1) {
    tmp = tmp.substring(tmp1, tmp.length);
    var start = tmp.indexOf("=", 0) + 1;
    var end = tmp.indexOf(";", start);
    return unescape(tmp.substring(start, end));
  }
  return "";
}

function SetCookie(key, val) {
  document.cookie =
    key + "=" + escape(val) + ";expires=Fri, 31-Dec-2030 23:59:59;";
}

window.addEventListener("pageshow", function (event) {
  if (event.persisted) {
    // bfcache発動時の処理
    window.location.reload();
  }
});

// フィルタリングjQuery

$(function () {
  var $btn = $(".btn [data-filter]"),
    $list = $(".list [data-category]");

  $btn.on("click", function (e) {
    e.preventDefault();

    var $btnTxt = $(this).attr("data-filter");

    if ($btnTxt == "all") {
      $list
        .fadeOut()
        .promise()
        .done(function () {
          $list.addClass("animate").fadeIn();
        });
    } else {
      $list
        .fadeOut()
        .promise()
        .done(function () {
          $list
            .filter('[data-category = "' + $btnTxt + '"]')
            .addClass("animate")
            .fadeIn();
        });
    }
  });
});
