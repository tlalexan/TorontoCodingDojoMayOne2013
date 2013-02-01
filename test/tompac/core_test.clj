(ns tompac.core-test
  (:use clojure.test tompac.core))

(deftest board-width-test 
  (testing "board width"
    (is (= 1 (board-width [[0]]     )))
    (is (= 2 (board-width [[0 0]]   )))
    (is (= 3 (board-width [[0 0 0]] )))))


(deftest board-height-test 
  (testing "board height"
    (is (= 1 (board-height [[0]] )))
    (is (= 2 (board-height [[0]
                            [0]] )))
    (is (= 3 (board-height [[0]
                            [0]
                            [0]] )))))

(deftest dimentions-test 
  (is (= [3 2] (dimensions [[1 2 3]
                            [4 5 6]]))))

(deftest next-pos-test
  (testing "next-pos up"
    (is (= (next-pos [0 1]    :up) [0 0])))
  (testing "next-pos down"
    (is (= (next-pos [0 0]  :down) [0 1])))
  (testing "next-pos left"
    (is (= (next-pos [1 0]  :left) [0 0])))
  (testing "next-pos right"
    (is (= (next-pos [0 0] :right) [1 0])))
  (testing "next pos other"
    (is (= (next-pos [1 1] :other) [1 1]))))

(deftest learn-about-mod-2 
  (is (= (mod -1 2) 1))
  (is (= (mod  0 2) 0))
  (is (= (mod  1 2) 1))
  (is (= (mod  2 2) 0)))

(deftest wrap-test
  (testing "within  2x2 board"
    (is (= (wrap   [2 2] [ 1  0]) [1 0])))
  (testing "outside 2x2 board left"
    (is (= (wrap   [2 2] [-1  0]) [1 0])))
  (testing "outside 2x2 right"
    (is (= (wrap   [2 2] [ 2  0]) [0 0])))
  (testing "within  3x2 board"
    (is (= (wrap   [3 2] [ 1  0]) [1 0])))
  (testing "outside 3x2 board left"
    (is (= (wrap   [3 2] [-1  0]) [2 0])))
  (testing "outside 3x2 right"
    (is (= (wrap   [3 2] [ 3  0]) [0 0])))
  (testing "outside 2x2 board up"
    (is (= (wrap   [2 2] [ 0 -1]) [0 1])))
  (testing "outside 2x2 board down"
    (is (= (wrap   [2 2] [ 0  2]) [0 0])))
  (testing "within  2x3 board"
    (is (= (wrap   [2 3] [ 1  0]) [1 0])))
  (testing "outside 2x3 board up"
    (is (= (wrap   [2 3] [ 0 -1]) [0 2])))
  (testing "outside 3x2 board down"
    (is (= (wrap   [2 3] [ 0  3]) [0 0]))))

(deftest move-on-board-test
  (testing "Move left on board"
    (is (= (move [[0 0]
                         [0 0]] [1 0]  :left) [0 0])))
  (testing "Move right on board"
    (is (= (move [[0 0]
                         [0 0]] [0 0] :right) [1 0])))
  (testing "Move up on board"
    (is (= (move [[0 0]
                         [0 0]] [0 1]    :up) [0 0])))
  (testing "Move down"
    (is (= (move [[0 0]
                         [0 0]] [0 0]  :down) [0 1]))))

(deftest move-wrap-around-rectangular-board-test
  (testing "Move up wrapping around board"
    (is (= (move [[0 0 0]
                  [0 0 0]] [0 0]    :up) [0 1])))
  (testing "Move down wrapping around board"
    (is (= (move [[0 0 0]
                  [0 0 0]] [0 1]  :down) [0 0])))
  (testing "Move left wrapping around board"
    (is (= (move [[0 0 0]
                  [0 0 0]] [0 0]  :left) [2 0])))
  (testing "Move right wrapping around board"
    (is (= (move [[0 0 0]
                  [0 0 0]] [2 0] :right) [0 0])))
  (testing "Move up the right column"
    (is (= (move [[0 0 0]
                  [0 0 0]] [2 1] :up) [2 0]))))

(deftest get-board-at-position-test
  (testing "getting the board value at a position"
    (is (= (board-at-pos [[1 2 3]
                          [4 5 6]] [0 0]) 1)))
  (testing "getting the board value at a position"
    (is (= (board-at-pos [[1 2 3]
                          [4 5 6]] [1 0]) 2)))
  (testing "getting the board value at a position"
    (is (= (board-at-pos [[1 2 3]
                          [4 5 6]] [1 1]) 5))))
                
(deftest wall-test
  (testing "open space up"
    (is (= (wall? [[0 0]
                   [0 0]] [0 1]    :up) false)))
  (testing "wall up"
    (is (= (wall? [[1 1]
                   [0 0]] [0 1]    :up) true)))
  (testing "wall down"
    (is (= (wall? [[0 0]
                   [1 1]] [0 0]  :down) true)))
  (testing "wall left wrapping around board"
    (is (= (wall? [[1 0]
                   [1 0]] [1 0]  :left) true)))
  (testing "wall right wrapping around board"
    (is (= (wall? [[0 1]
                   [0 1]] [0 0] :right) true))))

