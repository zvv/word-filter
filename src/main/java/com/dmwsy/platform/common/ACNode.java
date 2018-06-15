package com.dmwsy.platform.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ACNode implements Serializable {

    private static final long serialVersionUID = 5912122461482105554L;

    public String leaf = null;
    public Map branches = new HashMap<String, ACNode>();
    public List fruits = new ArrayList<Info>();
    public List link = new ArrayList<ACNode>();
    public ACNode bud = null;
    public int level = 0;

    public ACNode() {
    }

    public ACNode(String leaf_) {
        this.leaf = leaf_;
    }

    public void setLeaf(String leaf_) {
        this.leaf = leaf_;
    }

    public String getLeaf() {
        return this.leaf;
    }

    public void setLevel(int level_) {
        this.level = level_;
    }

    public int getLevel() {
        return this.level;
    }

    public ACNode addBranch(String branch_) {
        ACNode newnode = null;
        if (!branches.containsKey(branch_)) {
            newnode = new ACNode(branch_);
            newnode.setLevel(this.level + 1);
            branches.put(branch_, newnode);
        } else {
            newnode = (ACNode) branches.get(branch_);
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

    public void addLink(ACNode node) {
        this.link.add(node);
    }

    public void addBud(ACNode bud_) {
        this.bud = bud_;
    }

    public ACNode checkBranch(String branch_) {
        if (branches.containsKey(branch_)) {
            return (ACNode) branches.get(branch_);
        } else {
            return null;
        }
    }

    public ACNode checkBud() {
        return this.bud;
    }
}
