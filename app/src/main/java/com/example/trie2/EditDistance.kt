package com.example.trie2

import kotlin.math.min

fun calculateEditDistance(inputText: String, comparatorText: String):Int {
    val dpList = Array(inputText.length+1) {
        IntArray(comparatorText.length + 1)
    }
    for(i in 0 until inputText.length+1) {
        dpList[i][0] = i
    }
    for (j in 0 until comparatorText.length+1) {
        dpList[0][j] = j
    }
    var i = 0
    while(++i <= inputText.length) {
        var j=0
        while(++j <= comparatorText.length) {
            if(inputText[i-1] == comparatorText[j-1]) {
                dpList[i][j] = dpList[i-1][j-1]
            } else {
                dpList[i][j] = min(dpList[i-1][j],(min(dpList[i][j-1],dpList[i-1][j-1]))) + 1
            }
        }
    }
    return dpList[inputText.length][comparatorText.length]
}

fun main() {
    print(calculateEditDistance("intention","execution"))
}