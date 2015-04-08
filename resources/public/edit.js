

editorLoaded = false;
waitingFunctions = [];

myOnload = function (f) {
    if(editorLoaded) {
        f();
    } else {
        waitingFunctions.push(f);
    }
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



var createResultElement = function (html) {
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


createCodePad = function (element, exercice_id) {
    return {allCoderes:[],

            editor : CodeMirror(element,
                {autoCloseBrackets: true,
                styleActiveLine: true,
                lineNumbers: true,
                lineWrapping: true}),

            checkChanges : function() {
                this.allCoderes = this.allCoderes.map(function (codePiece) {
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
                this.allCoderes = this.allCoderes.filter(function(x) {return x;});},

            clearEval : function (from, to) {
                findIntersectedMarks(this.editor,
                    CodeMirror.Pos(from.line, from.pos),
                    CodeMirror.Pos(to.line, to.pos)).forEach(function(m) {
                        m.clear();
                        m.coderes.lineWidget.clear();
                    });},

            createEvalResult : function (evalres) {
                var cm = this.editor;
                this.clearEval(evalres.start, evalres.end);
                var coderes = {markedText : cm.markText(CodeMirror.Pos(evalres.start.line, evalres.start.pos),
                                            CodeMirror.Pos(evalres.end.line,
                                                           evalres.end.pos),
                                            {css: ("background-color: hsl(" + Math.floor(Math.random()*255) + ", 90%, 95%) "),
                                             clearWhenEmpty:true}),
                   lineWidget : cm.addLineWidget(evalres.end.line, createResultElement(evalres.eval))};
                coderes.markedText.coderes = coderes;
                this.allCoderes.push(coderes);},


            initEditor : function () {
                var tthis = this;
                this.editor.on("changes", function() {tthis.checkChanges;});
                this.editor.addKeyMap({"Cmd-Enter" : function(cm) {
                    var cursor = cm.getCursor();
                    $.post("/eval", {text:cm.getValue(), line : cursor.line, pos : cursor.ch}).done(function(data) {
                        tthis.createEvalResult(jQuery.parseJSON(data));
                    });
                }});
                return tthis;
            }


    }.initEditor();
};


editorLoaded = true;
waitingFunctions.forEach(function(f) {f();});


}, function () {
    console.log("error loading scripts");}


                                );

