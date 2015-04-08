



// THIS IS TERRIBLE
function loadDep(filename, filetype){
    if (filetype=="js"){ //if filename is a external JavaScript file
        var fileref=document.createElement('script');
        fileref.setAttribute("type","text/javascript");
        fileref.setAttribute("src", filename);
    }
    else if (filetype=="css"){ //if filename is an external CSS file
        var fileref=document.createElement("link")
        fileref.setAttribute("rel", "stylesheet")
        fileref.setAttribute("type", "text/css")
        fileref.setAttribute("href", filename)
    }
    if (typeof fileref!="undefined")
        document.getElementsByTagName("head")[0].appendChild(fileref)
        }






var loadScripts = ["/codemirror-5.1/lib/codemirror.js",
 "/codemirror-5.1/addon/edit/closebrackets.js",
 "/codemirror-5.1/mode/clojure/clojure.js",
 "/codemirror-5.1/addon/display/placeholder.js",
 "/codemirror-5.1/addon/runmode/runmode.js"]


var loadedScripts = loadScripts.reduce(function (p, next) {
    var f = function() {return $.getScript(next);};
    if(p) {
        return p.then(f, function() {
            console.log(arguments);});
    }
    else {
        return f();
    }
}, undefined);



function loadCss(s) {
      $('<link/>', {
   rel: 'stylesheet',
   type: 'text/css',
   href: s
}).appendTo('head');

};

loadedScripts.then(function () {


loadCss("/codemirror-5.1/lib/codemirror.css");





allCoderes = []



var editor = CodeMirror.fromTextArea(document.getElementById("code"),
                                     {autoCloseBrackets: true,
                                      styleActiveLine: true,
                                      lineNumbers: true,
                                      lineWrapping: true
                                     });


editor.on("changes", function(target, changes) {


    allCoderes = allCoderes.map(function (codePiece) {
        if(codePiece.markedText.find()) {

            var textLine = codePiece.markedText.find().to.line;

            var widgetLine = codePiece.lineWidget.line.lineNo();
            if(textLine != widgetLine) {
                var oldWidget = codePiece.lineWidget;
                codePiece.lineWidget = editor.addLineWidget(textLine,codePiece.lineWidget.node);
                oldWidget.clear();
            }
            return codePiece;

        } else {
            if(codePiece.lineWidget) {
                codePiece.lineWidget.clear();
            }
            return undefined;

        }
    });

    allCoderes = allCoderes.filter(function(x) {return x;});

});


var createYoloElement = function (html) {
    var outer = document.createElement('div');
    var inner = document.createElement('div');
    outer.appendChild(inner);
    inner.className = "EvalResult";

    var appendToInner = function(s) {
        inner.innerHTML += s;
    };

    html.split("\n").forEach(function (s) {
        var dumb = document.createElement('div');
        CodeMirror.runMode(s, "text/x-clojure", dumb);
        inner.appendChild(dumb);
        appendToInner("<br>");
    });

    inner.removeChild(inner.lastChild);

    return outer;
}

var comparePos = function (a,b) {
    if(a.line == b.line) {
        return a.ch - b.ch
    } else {
        return a.line - b.line
    }
}

var findIntersectedMarks = function (editor, from, to) {
    return editor.getAllMarks().filter(function (m) {
        var f = m.find();
        return (comparePos(f.to, from) > 0 && comparePos(to, f.from) > 0);
    });
};


var clearEval = function (from, to) {
    findIntersectedMarks(editor, CodeMirror.Pos(from.line, from.pos),
                         CodeMirror.Pos(to.line, to.pos)).forEach(function(m) {
        m.clear();
        m.coderes.lineWidget.clear();
    }
                                                                 );
};

var createEvalResult = function (cm, evalres) {
    clearEval(evalres.start, evalres.end);


    var coderes = {markedText : cm.markText(CodeMirror.Pos(evalres.start.line, evalres.start.pos),
                                            CodeMirror.Pos(evalres.end.line,
                                                           evalres.end.pos),
                                            {css: ("background-color: hsl(" + Math.floor(Math.random()*255) + ", 90%, 95%) "),
                                             clearWhenEmpty:true}),
                   lineWidget : cm.addLineWidget(evalres.end.line, createYoloElement(evalres.eval))};

    coderes.markedText.coderes = coderes;

    allCoderes.push(coderes);



};

editor.addKeyMap({"Cmd-Enter" : function(cm) {
    var cursor = cm.getCursor();
    $.post("/eval", {text:cm.getValue(), line : cursor.line, pos : cursor.ch}).done(function(data) {
        createEvalResult(cm, jQuery.parseJSON(data));
    });


}});
}, function () {
    console.log("error loading scripts");}


                                );

