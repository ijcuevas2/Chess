(TeX-add-style-hook
 "TestPlan"
 (lambda ()
   (TeX-run-style-hooks
    "latex2e"
    "article"
    "art10"
    "mathtools"
    "tikz"
    "graphicx"
    "float"
    "verbatim"))
 :latex)

