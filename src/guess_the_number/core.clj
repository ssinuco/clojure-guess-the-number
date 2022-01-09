(ns guess-the-number.core
  (:gen-class))

;; *** Helpers functions ***

(defn get-random-number
  "Returns a random integer between 1 and n (inclusive)."
  [n]
  (+ (rand-int (- n 1)) 1))


(defn get-input
  "Waits for user to enter text and hit enter, then cleans the input and casts it to integer"
  []
  (Integer/parseInt (clojure.string/trim (read-line))))

(defn greater-than-1?
  [n]
  (when (> n 1) n))

;; *** Side effects functions ***

(defn get-upper-bound
  "Gets from user a valid input for upper bound of guessing range"
  []
  (println "Enter the upper bound of guessing range:")
  (try (let [input (greater-than-1? (get-input))]
         (if (nil? input)
           (throw (Exception. "Invalid upper bound"))
           input))
       (catch Exception e
         (println "Invalid upper bound. Try again")
         (get-upper-bound))))

(defn process-input
  "Compares the random number with user guess"
  [guess random_number]
  (compare guess random_number))

(defn guess
  ""
  [random_number upper_bound total_tries]
  (println (str  "Guess a number between 1 and " upper_bound ":"))
  (try 
    (let [input (get-input)]
      (let [result (process-input input random_number)]
        (case result
          -1 (do 
               (println "Sorry, guess again. Too low.")
               (guess random_number upper_bound (inc total_tries)))
          0 (println (str "Ya, congrats. You have guessed the number correctly! " "Total tries: " total_tries))
          1 (do 
              (println "Sorry, guess again. Too high.")
              (guess random_number upper_bound (inc total_tries))))))
    (catch Exception e
      (println "Invalid input. Try again.")
      (guess random_number upper_bound (inc total_tries)))
    ))

(defn start-game
  "Starts a new game"
  []
  (let [
        upper_bound (get-upper-bound)
        random_number (get-random-number upper_bound)]
    (guess random_number upper_bound 1)))

;; *** main functions ***

(defn -main
  [& args]
  (println "Get ready to play guess the number!")
  (start-game))

  
