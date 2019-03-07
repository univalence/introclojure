(ns leiningen.midje-doc.run.renderer
  (:require [hiccup.core :as html]
            [introclojure.exercice]
            [markdown.core :refer [md-to-html-string]]
            [me.raynes.conch :refer [programs]]
            :reload))

(programs pygmentize)

(def ^:dynamic *plain* true)

(defn basic-html-escape
  [data]
  (clojure.string/escape data { \< "&lt;" \> "&gt;" \& "&amp;" \" "&quot;" }))

(defn basic-html-unescape
  [data]
  (let [out (-> data
                (.replaceAll "&amp;quot;" "&quot;")
                (.replaceAll "&amp;lt;" "&lt;")
                (.replaceAll "&amp;gt;" "&gt;")
                (.replaceAll "&amp;amp;" "&amp;"))]
    out))

(defn adjust-facts-code [s spaces]
  (let [i-arrow (.lastIndexOf s "=>")]
    (if (<= 0 i-arrow)
        (let [i-nl  (.lastIndexOf s "\n" i-arrow)
              rhs (-> (.substring s i-nl)
                      (.replaceAll (str "\n" spaces) "\n"))
              lhs (.substring s 0 i-nl)]
          (str lhs rhs))
        s)))

(defn adjust-indented-code [s spaces]
  (let [fst-idx (.indexOf s "\n")]
    (if (< fst-idx 0) s
      (let [rst-ori (.substring s fst-idx)
          rst-new (.replaceAll rst-ori (str "\n" spaces) "\n")
          fst-str (.substring s 0 fst-idx)]
        (str fst-str rst-new)))))


(import 'java.security.MessageDigest
        'java.math.BigInteger)

(defn chas [^String s ^Integer n]
  (let [algorithm (MessageDigest/getInstance "MD5")
        raw (.digest algorithm (.getBytes s))]
    (int (mod (BigInteger. 1 raw) n))))

(def who ["Jonathan" "Philippe" "Brahim" "François" "Bernarith" "Mohamed" "Harrison"])


(defn render-exercice [elem]

  (let [[title exs solution] (:content elem)

        eid (introclojure.exercice/store-and-get-id elem)]

   [:div {:class "exercice-box" :id eid}

    [:h4 "Exercice : " title  "  -  (" (who (chas title (count who))) ")"]
    [:div

      [:div {:class "constraints"}
       "Résoudre avec les contraintes suivantes"
       (for [e exs]

         (let [cid (introclojure.exercice/store-and-get-id [elem e])]

       [:div {:id cid :class "constraint"}(-> e str basic-html-escape)]
       ))

      ]


      [:div {:class "spad"}
       [:script {:type "text/javascript"}
       "myOnload(function() {createCodePad($(\"#" eid  " .spad\")[0], \"" eid "\");});"
       ]

      ]
     ]
     ]
  ))


(defn render-element [elem]
  (condp = (:type elem)

    :chapter
    [:div
     (if (:tag elem) [:a {:name (:tag elem)}])
     [:h2 [:b (str (:num elem) " &nbsp;&nbsp; " (:title elem))]]]
    :section
    [:div
     (if (:tag elem) [:a {:name (:tag elem)}])
     [:h3 (str (:num elem) " &nbsp;&nbsp; " (:title elem))]]
    :subsection
    [:div
     (if (:tag elem) [:a {:name (:tag elem)}])
     [:h3 [:i (str (:num elem) " &nbsp;&nbsp; " (:title elem))]]]
    :subsubsection
    [:div
     (if (:tag elem) [:a {:name (:tag elem)}])
     [:h3 [:i (str (:num elem) " &nbsp;&nbsp; " (:title elem))]]]

    :paragraph [:div (basic-html-unescape (md-to-html-string (:content elem)))]

    :image
    [:div {:class "figure"}
     (if (:tag elem) [:a {:name (:tag elem)}])
     (if (:num elem)
       [:h4 [:i (str "fig." (:num elem)
                     (if-let [t (:title elem)] (str "  &nbsp;-&nbsp; " t)))]])
     [:div {:class "img"} [:img (dissoc elem :num :type :tag)]]
     [:p]]

    :ns
    [:div
     (if *plain*
       [:pre (-> elem :content basic-html-escape)]
       (pygmentize  "-f" "html" "-l" (or (:lang elem) "clojure")
                    {:in (:content elem)}))]
    :code

    [:div
     (if (:tag elem) [:a {:name (:tag elem)}])
     (if (:num elem)
       [:h4 [:i (str "e." (:num elem)
                     (if-let [t (:title elem)] (str "  &nbsp;-&nbsp; " t)))]])
     (if *plain*
       [:pre (-> (:content elem)
                 (basic-html-escape)
                 (adjust-indented-code (apply str (repeat (or (:fact-level elem) 0) "  "))))]
       (let [output
             (pygmentize  "-f" "html" "-l" (or (:lang elem) "clojure")
                          {:in (adjust-indented-code (:content elem)
                                                     (apply str (repeat (or (:fact-level elem) 0) "  ")))})]
         ;;(do (println output))
         output))]

    :exercice
    (render-exercice elem)


    (throw (Exception. (str "not matching clause on " elem)))

    ))





(defn render-toc-element [elem]
  (case (:type elem)
    :chapter [:h4
              [:a {:href (str "#" (:tag elem))} (str (:num elem) " &nbsp; " (:title elem))]]

    :section [:h5 "&nbsp;&nbsp;"
              [:i [:a {:href (str "#" (:tag elem))} (str (:num elem) " &nbsp; " (:title elem))]]]

    :subsection [:h5 "&nbsp;&nbsp;&nbsp;&nbsp;"
                [:i [:a {:href (str "#" (:tag elem))} (str (:num elem) " &nbsp; " (:title elem))]]]))

(defn render-toc [elems]
  (let [telems (filter #(#{:chapter :section :subsection} (:type %)) elems)]
    (map render-toc-element telems)))


(defn render-elements [elems]
  (html/html
   (map render-element elems)))

(defn slurp-res [path]
  (slurp (or (try (clojure.java.io/resource path)
                  (catch java.io.FileNotFoundException e))
             (str "resources/" path))))


(defn render-heading [document]
  [:div {:class "heading"}
   [:h1 (:title document)]
   [:h3 (:sub-title document)]
   [:hr]
   [:div {:class "info"}
    (if-let [author (:author document)]
      [:h5 "Author: " author
       (if-let [email (:email document)]
         [:b "&nbsp;&nbsp;" [:a {:href (str "mailto:" email)} "(" email ")"] ])])
    (if-let [version (:version document)]
      [:h5 "Library: v" version])
    (if-let [rev (:revision document)]
      [:h5 "Revision: v" rev])
    [:h5 "Date: " (.format
                   (java.text.SimpleDateFormat. "dd MMMM yyyy")
                   (java.util.Date.))]
    (if-let [url (:url document)]
      [:h5 "Website: " [:a {:href url} url]])
    (if (not (false? (:advertise document)))
      [:h5 "Generated By: " [:a {:href "http://www.github.com/zcaudate/lein-midje-doc"}
                                 "MidjeDoc"]])]
   [:br]
   [:hr]])

(defn render-tracking [document]
  (if (:tracking document)
    [:script
      (str "var _gaq = _gaq || [];
        _gaq.push(['_setAccount', '" (:tracking document) "']);
        _gaq.push(['_trackPageview']);

        (function() {
          var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
          ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
          var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
        })();")]))

(defn render-html-doc [output document elems]
  (let [heading (render-heading document)]
    (spit output
          (html/html
           [:html
            [:head
             [:meta {:http-equiv "X-UA-Compatible" :content "chrome=1"}]
             [:meta {:charset "utf-8"}]
             [:meta {:name "viewport" :content "width=device-width, initial-scale=1, user-scalable=no"}]
             [:title (or (:window document) (:title document))]
             [:style
              (str
               (slurp-res "template/stylesheets/styles.css")
               "\n\n"
               (slurp-res "template/stylesheets/pygment_trac.css")
               "\n\n")]

              ]
            [:body
             [:header
              heading
              (render-toc elems)
              [:br]]
             [:section
              heading
              (map render-element elems)]]
            [:script {:type "text/javascript"}
             (slurp-res "template/javascripts/scale.fix.js")]
            (render-tracking document)]))))





 (defn render-html-doc2 [elems]
          (html/html
           [:html
            [:head
             [:meta {:http-equiv "X-UA-Compatible" :content "chrome=1"}]
             [:meta {:charset "utf-8"}]
             [:meta {:name "viewport" :content "width=device-width, initial-scale=1, user-scalable=no"}]

             [:style
              (str
               (slurp-res "template/stylesheets/styles.css")
               "\n\n"
               (slurp-res "template/stylesheets/pygment_trac.css")
               "\n\n")]

             (for [s ["/jquery/jquery-1.11.2.min.js"

                      "/codemirror-5.1/lib/codemirror.js",
                       "/codemirror-5.1/addon/edit/closebrackets.js",
                       "/codemirror-5.1/mode/clojure/clojure.js",
                       "/codemirror-5.1/addon/display/placeholder.js",
                       "/codemirror-5.1/addon/runmode/runmode.js"
                      "/edit.js"
                      ]]
               [:script {:type "text/javascript" :src s}]
              )
             [:link {:href "/css/style2.css" :rel "stylesheet" :type "text/css"}]

              ]
            [:body
             [:header
              (render-toc elems)
              [:br]]
             [:section
              (map render-element elems)]]
            [:script {:type "text/javascript"}
             (slurp-res "template/javascripts/scale.fix.js")]]))

