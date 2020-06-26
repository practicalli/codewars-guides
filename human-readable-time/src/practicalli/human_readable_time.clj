(ns practicalli.human-readable-time)

;; Write a function, which takes a non-negative integer (seconds) as input and returns the time in a human-readable format (HH:MM:SS)

;; HH = hours, padded to 2 digits, range: 00 - 99
;; MM = minutes, padded to 2 digits, range: 00 - 59
;; SS = seconds, padded to 2 digits, range: 00 - 59

;; The maximum time never exceeds 359999 (99:59:59)

;; You can find some examples in the test fixtures.

(defn human-readable
  [x]
  (format "%02d:%02d:%02d"
          (quot x 3600)
          (quot (rem x 3600) 60)
          (rem x 60)))


;; REPL driven development
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(comment

  ;; calculate hours - divide by (* 60 60) - 3600

  (def hours-divider
    (* 60 60))

  (def big-time 359999)

  (quot 22 10)
  (/ 22 10.0)

  (quot big-time hours-divider)
  ;; => 99

  (rem big-time hours-divider)
  ;; => 3599


  ;; If the remainder is less than hours-divider,
  ;; then get the quot and rem using the minutes divider

  (def minutes-divider 60)

  (quot
    (rem big-time hours-divider)
    minutes-divider)
  ;; => 59

  (/ 3599 60.0)
  ;; => 3599/60

  (type
    (/ 3599 60))

  (rem
    (rem big-time hours-divider)
    minutes-divider)
  ;; => 59


  (quot 4 100)
  ;; so we could solve it with a very simple let binding


  (defn human-readable
    [x]
    (let [hours   (quot x 3600)
          minutes (quot (rem x 3600) 60)
          seconds (rem  (rem x 3600) 60)]
      ;; map [hours minutes seconds]
      (apply str hours ":" minutes ":" seconds)))

  (clojure.string/join ":" [59 59 59] )

  (human-readable 359999)
  ;; => "99:59:59"


  (human-readable 0)
  ;; => "0:0:0"
  (human-readable 59)
  ;; => "0:0:59"
  (human-readable 60)
  ;; => "0:1:0"
  (human-readable 90)
  ;; => "0:1:30"
  (human-readable 86399)
  ;; => "23:59:59"


  (map (fn [value] (if (< (count value) 2 )
                     (str "0" value)
                     value)))


  ;; The values are correct, however, we need to put in the padding.

  (format "%02d" 10)



  (defn human-readable
    [x]
    (let [hours   (quot x 3600)
          minutes (quot (rem x 3600) 60)
          seconds (rem  (rem x 3600) 60)]
      (format "%02d" hours ":" minutes ":" seconds)))


  (human-readable 59)


  (defn human-readable
    [x]
    (let [hours   (format "%02d" (quot x 3600))
          minutes (format "%02d" (quot (rem x 3600) 60))
          seconds (format "%02d" (rem  (rem x 3600) 60))]
      (str hours ":" minutes ":" seconds)))



  ;; the seconds can be simplified

  (defn human-readable
    [x]
    (let [hours   (quot x 3600)
          minutes (quot (rem x 3600) 60)
          seconds (rem x 60)]
      (format "%02d:%02d:%02d" hours minutes seconds)))

  (human-readable 59)



  ;; We can optimise by dropping the local names
  ;; and just make one format expression

  (defn human-readable
    [x]
    (format "%02d:%02d:%02d"
            (quot x 3600)
            (quot (rem x 3600) 60)
            (rem x 60)))


  (human-readable 59)

  ;; recursive function?
  ;; reducing function?

  )
