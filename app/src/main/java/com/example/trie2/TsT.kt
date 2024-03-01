package com.example.trie2

class TernarySearchTree {
    private var root: Node? = null

    private class Node(
        var char: Char,
        var isEndOfWord: Boolean = false,
        var left: Node? = null,
        var middle: Node? = null,
        var right: Node? = null,
        var adId: Int? = null
    )

    fun insert(word: String,adId: Int) {
        root = insert(root, word.toCharArray(), 0,adId)
    }

    private fun insert(node: Node?, word: CharArray, index: Int,adId: Int): Node {
        var currentNode = node
        if (currentNode == null) {
            currentNode = Node(word[index])
        }

        if (word[index] < currentNode.char) {
            currentNode.left = insert(currentNode.left, word, index,adId)
        } else if (word[index] > currentNode.char) {
            currentNode.right = insert(currentNode.right, word, index,adId)
        } else if (index < word.size - 1) {
            currentNode.middle = insert(currentNode.middle, word, index + 1,adId)
        } else {
            currentNode.isEndOfWord = true
            currentNode.adId = adId
        }

        return currentNode
    }

    fun searchPrefix(prefix: String): List<Pair<String,Int?>> {
        val result = mutableListOf<Pair<String,Int?>>()
        val prefixChars = prefix.toCharArray()
        val prefixNode = searchPrefix(root, prefixChars, 0)

        if(prefixNode?.isEndOfWord == true) {
            result.add(prefix to prefixNode.adId)
        }

        if (prefixNode != null) {
            collectWords(prefixNode.middle, StringBuilder(prefix), result)
        }

        return result
    }

    private fun searchPrefix(node: Node?, prefix: CharArray, index: Int): Node? {
        var currentNode = node
        var currentIndex = index

        while (currentNode != null && currentIndex < prefix.size) {
            when {
                prefix[currentIndex] < currentNode.char -> currentNode = currentNode.left
                prefix[currentIndex] > currentNode.char -> currentNode = currentNode.right
                else -> {
                    if (currentIndex == prefix.size - 1) return currentNode
                    currentNode = currentNode.middle
                    currentIndex++
                }
            }
        }

        return null
    }

    private fun collectWords(node: Node?, prefix: StringBuilder, result: MutableList<Pair<String,Int?>>) {
        node ?: return

        if (node.isEndOfWord) {
            result.add(prefix.toString() + node.char to node.adId)
        }

        collectWords(node.left, prefix, result)

        val prefixCopy = StringBuilder(prefix)
        prefixCopy.append(node.char)
        collectWords(node.middle, prefixCopy, result)

        collectWords(node.right, prefix, result)
    }
}

fun main() {
   val tst = TernarySearchTree()
    tst.insert("akshat",1)
    tst.insert("akash",3)
    tst.insert("akhil",1)
    tst.insert("akshay",6)
    tst.insert("akshaydnhjw kwd",1)
   println( tst.searchPrefix("ak"))
}

