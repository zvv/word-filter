package com.dmwsy.platform.common;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TrieTree implements Serializable {

    private static final long serialVersionUID = 5912122461482105560L;

    protected TrieNode root;
    protected Map core = new HashMap<Long, TrieNode>();

    public TrieTree() {
        this.root = new TrieNode();
    }

    public void addBranch(Info fruit_) {
        Deque queue = new ArrayDeque<TrieNode>();
        String[] strarray = fruit_.getString();
        for (String str : strarray) {
            String[] strs = str.split(" ");
            TrieNode currentnode = this.root;
            for (int i = 0; i < strs.length; ++i) {
                currentnode = currentnode.addBranch(strs[i]);
                queue.add(currentnode);
            }
            currentnode.addFruit(fruit_);
            queue.pollLast();
            TrieNode tempnode;
            while ((tempnode = (TrieNode) queue.pollLast()) != null) {
                tempnode.addBud(currentnode);
            }
        }

        strarray = fruit_.getPinYin();
        for (String str : strarray) {
            str = str.replaceAll(" ", "");
            str = StringMachine.insertBlank(str);
            String[] strs = str.split(" ");
            TrieNode currentnode = this.root;
            for (int i = 0; i < strs.length; ++i) {
                currentnode = currentnode.addBranch(strs[i]);
                queue.add(currentnode);
            }
            currentnode.addFruit(fruit_);
            queue.pollLast();
            TrieNode tempnode;
            while ((tempnode = (TrieNode) queue.pollLast()) != null) {
                tempnode.addBud(currentnode);
            }
        }

        addCore(fruit_);
    }

    public void addCore(Info fruit_) {
        this.core.put(fruit_.getId(), fruit_);
    }

    public void delCore(Info fruit_) {
        this.core.remove(fruit_.getId());
    }

    private void delBranch(Info fruit_, Deque queue) {
        if (queue.size() > 1) {
            TrieNode endnode = (TrieNode) queue.pollLast();
            if (endnode.delFruit(fruit_) && endnode.countFruit() == 0) {
                TrieNode currentnode = endnode;
                String leaf = currentnode.getLeaf();
                while (currentnode.countBranch() == 0 && currentnode.countFruit() == 0
                        && (currentnode = (TrieNode) queue.pollLast()) != null) {
                    currentnode.delBranch(leaf);
                    currentnode.delBud(endnode);
                    leaf = currentnode.getLeaf();
                }
                while ((currentnode = (TrieNode) queue.pollLast()) != null) {
                    currentnode.delBud(endnode);
                }
            }
        }
    }

    public void delBranch(Info fruit_) {

        if (!checkCore(fruit_)) {
            return;
        }

        Info fruit2del = (Info) this.core.get(fruit_.getId());

        Deque queue = new ArrayDeque<TrieNode>();
        String[] strarray = fruit2del.getString();
        for (String str : strarray) {
            String[] strs = str.split(" ");
            TrieNode currentnode = this.root;
            queue.add(currentnode);
            for (int i = 0; i < strs.length; ++i) {
                if (currentnode.checkBranch(strs[i]) != null) {
                    currentnode = currentnode.checkBranch(strs[i]);
                    queue.add(currentnode);
                } else {
                    break;
                }
            }
            delBranch(fruit2del, queue);
            queue.clear();
        }

        strarray = fruit2del.getPinYin();
        for (String str : strarray) {
            str = str.replaceAll(" ", "");
            str = StringMachine.insertBlank(str);
            String[] strs = str.split(" ");
            TrieNode currentnode = this.root;
            queue.add(currentnode);
            for (int i = 0; i < strs.length; ++i) {
                if (currentnode.checkBranch(strs[i]) != null) {
                    currentnode = currentnode.checkBranch(strs[i]);
                    queue.add(currentnode);
                } else {
                    break;
                }
            }
            delBranch(fruit2del, queue);
            queue.clear();
        }

        delCore(fruit2del);
    }

    public List findBranch(String text) {
        String[] strs = text.split(" ");
        TrieNode currentnode = this.root;
        for (int i = 0; i < strs.length; ++i) {
            if (currentnode.checkBranch(strs[i]) != null) {
                currentnode = currentnode.checkBranch(strs[i]);
                // System.out.println(currentnode.getLeaf());
            } else {
                return new ArrayList<Info>();
            }
        }

        List ret = new ArrayList<Info>();
        ret.addAll(currentnode.fruits);

        Iterator<TrieNode> iter = currentnode.buds.iterator();
        while (iter.hasNext()) {
            ret.addAll(iter.next().fruits);
        }

        return ret;
    }

    public boolean checkCore(Info fruit_) {
        return this.core.containsKey(fruit_.getId());
    }

    public boolean checkCore(long id) {
        return this.core.containsKey(id);
    }
}
