(ns printer.core)

;; Kata: Printer Errors
;; Level: 7kyu
;; URL: https://www.codewars.com/kata/56541980fa08ab47a0000040/


;; Instructions
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; In a factory a printer prints labels for boxes. For one kind of boxes the printer has to use colors which, for the sake of simplicity, are named with letters from a to m.

;; The colors used by the printer are recorded in a control string. For example a "good" control string would be aaabbbbhaijjjm meaning that the printer used three times color a, four times color b, one time color h then one time color a...

;; Sometimes there are problems: lack of colors, technical malfunction and a "bad" control string is produced e.g. aaaxbbbbyyhwawiwjjjwwm with letters not from a to m.

;; You have to write a function printer_error which given a string will output the error rate of the printer as a string representing a rational whose numerator is the number of errors and the denominator the length of the control string. Don't reduce this fraction to a simpler expression.

;; The string has a length greater or equal to one and contains only letters from ato z.

;; #Examples:

;; s="aaabbbbhaijjjm"
;; error_printer(s) => "0/14"

;; s="aaaxbbbbyyhwawiwjjjwwm"
;; error_printer(s) => "8/22"


;; Analysis
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Requirement:
;; - count the number of error codes in a given string of printer codes
;; - show error codes over total number of codes - as a fraction (not decimal)
;; - return the result as a string

;; Input:
;; - A string containing printer codes, represented by charaters in the string.
;; - Only characters a to m are legal color codes for the printer
;; - Charactes n to z are error codes.

;; Approach
;; - filter the string to return only error codes
;; - cound the number of error codes
;; - count the total number of error codes
;; - return string with the result in the desired format


;; Design Journal
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Example printer string of color codes
#_(def u "aaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbmmmmmmmmmmmmmmmmmmmxyz")


;; Create a set of all the legal color codes,
;; used to identify those codes not legal (could have also used error codes)

#_(def legal-color-codes #{"a" "b" "c" "d" "e" "f" "g" "h" "i" "j" "k" "l" "m"})

;; need to use characters when working with a string as a sequence,
;; as a string is a sequence of characters
#_(def legal-color-chars #{\a \b \c \d \e \f \g \h \i \j \k \l \m})


;; Using remove and the set of legal color codes to find any error codes remaining

#_(defn printer-error [s]
    (let [total  (count s)
          errors (count (remove legal-color-chars (seq s)))]
      (str errors "/" total)))

#_(printer-error u)

;; Get total number of characters
#_(count u)

;; Convert string into sequence of characters
#_(seq u)

;; cannot filter a string with characters
#_(remove legal-color-codes (seq u))

#_(remove legal-color-chars (seq u))
;; => (\x \y \z)


#_(count
    (remove legal-color-chars (seq u)))


;; Solve with regex
;; Simpler to type, allows range of characters to be used without explicitly typing them all in.

;; doesnt work - a regular expression is not a function
#_(remove #"[a-m]" u)

;; create a new (lazy) sequence of the string
;; with only the characters n to z
#_(re-seq #"[n-z]" u)

;; then count the results

#_(count
    (re-seq #"[n-z]" u))


;; Put the regex statement back into our function definition.

#_(defn printer-error [s]
    (let [total  (count s)
          errors (count (re-seq #"[n-z]" s))]
      (str errors "/" total)))

;; improvements
;; - no external dictionary of characters required
;; - function self-contained, only values used are passed as arguments

;; We can do this without local name bindings,
;; making the code slightly more performant.

#_(defn printer-error [s]
    (str
      (count (re-seq #"[n-z]" s))
      "/"
      (count s))
    )


;; Solution
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;



(defn printer-error [s]
  (str
    (count (re-seq #"[n-z]" s))
    "/"
    (count s))
  )
