package com.dmwsy.platform.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TrieNode implements Serializable {

    private static final long serialVersionUID = 5912122461482105564L;

    public String leaf = null;
    public Map branches = new HashMap<String, TrieNode>();
    public List fruits = new LinkedList<Info>();
    public Set buds = new HashSet<TrieNode>();

    public TrieNode() {
    }

    public TrieNode(String leaf_) {
        this.leaf = leaf_;
    }

    public void setLeaf(String leaf_) {
        this.leaf = leaf_;
    }

    public String getLeaf() {
        return this.leaf;
    }

    public TrieNode addBranch(String branch_) {
        TrieNode newnode = null;
        if (!branches.containsKey(branch_)) {
            newnode = new TrieNode(branch_);
            branches.put(branch_, newnode);
        } else {
            newnode = (TrieNode) branches.get(branch_);
        }
        return newnode;
    }

    public void delBranch(String branch_) {
        this.branches.remove(branch_);
    }

    public int countBranch() {
        return this.branches.size();
    }

    public int findFruit(Info fruit_) {
        Iterator<Info> iter = this.fruits.iterator();
        long id = fruit_.getId();
        int index = 0;
        while (iter.hasNext()) {
            Info temp = iter.next();
            if (temp.getId() == id) {
                return index;
            }
            index++;
        }

        return -1;
    }

    public void addFruit(Info fruit_) {
        if (findFruit(fruit_) == -1) {
            this.fruits.add(fruit_);
        }
    }

    public boolean delFruit(Info fruit_) {
        int index = findFruit(fruit_);
        if (index != -1) {
            this.fruits.remove(index);
            return true;
        }

        return false;
    }

    public int countFruit() {
        return this.fruits.size();
    }

    public void addBud(TrieNode node) {
        if (!this.buds.contains(node)) {
            this.buds.add(node);
        }
    }

    public void delBud(TrieNode node) {
        this.buds.remove(node);
    }

    public int countBud() {
        return this.buds.size();
    }

    public TrieNode checkBranch(String branch_) {
        if (branches.containsKey(branch_)) {
            return (TrieNode) branches.get(branch_);
        } else {
            return null;
        }
    }
}
