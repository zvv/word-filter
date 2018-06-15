package com.dmwsy.platform.common;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class ACAutomaton implements Serializable {

    private static final long serialVersionUID = 5912122461482105550L;

    public static class Match {
        public int end;
        public int length;
        public Info info;

        public Match(int end_, int length_, Info info_) {
            this.end = end_;
            this.length = length_;
            this.info = info_;
        }

        public String toString() {
            return String.format("%s:%d,%d", this.info.toString(), this.end - this.length + 1, this.end);
        }
    }

    protected ACNode root;
    protected Map core = new HashMap<Long, ACNode>();

    public ACAutomaton() {
        this.root = new ACNode();
    }

    public void addCore(Info fruit_) {
        this.core.put(fruit_.getId(), fruit_);
    }

    public void delCore(Info fruit_) {
        this.core.remove(fruit_.getId());
    }

    public boolean checkCore(Info fruit_) {
        return this.core.containsKey(fruit_.getId());
    }

    public boolean checkCore(long id) {
        return this.core.containsKey(id);
    }

    public void addBranch(Info fruit_) {
        String[] strarray = fruit_.getString();
        for (String str : strarray) {
            String[] strs = str.split(" ");
            ACNode currentnode = this.root;
            for (int i = 0; i < strs.length; ++i) {
                currentnode = currentnode.addBranch(strs[i]);
            }
            currentnode.addFruit(fruit_);
        }

        // strarray = fruit_.getPinYin(); --添加拼音过滤
        for (String str : strarray) {
            str = str.replaceAll(" ", "");
            str = StringMachine.insertBlank(str);
            String[] strs = str.split(" ");
            ACNode currentnode = this.root;
            for (int i = 0; i < strs.length; ++i) {
                currentnode = currentnode.addBranch(strs[i]);
            }
            currentnode.addFruit(fruit_);
        }

        addCore(fruit_);
    }

    private void delBranch(Info fruit_, Deque queue) {
        if (queue.size() > 1) {
            ACNode endnode = (ACNode) queue.pollLast();
            if (endnode.delFruit(fruit_) && endnode.countFruit() == 0) {
                ACNode currentnode = endnode;
                String leaf = currentnode.getLeaf();
                while (currentnode.countBranch() == 0 && currentnode.countFruit() == 0
                        && (currentnode = (ACNode) queue.pollLast()) != null) {
                    currentnode.delBranch(leaf);
                    leaf = currentnode.getLeaf();
                }
            }
        }
    }

    public void delBranch(Info fruit_) {
        Deque queue = new ArrayDeque<ACNode>();
        String[] strarray = fruit_.getString();
        for (String str : strarray) {
            String[] strs = str.split(" ");
            ACNode currentnode = this.root;
            queue.add(currentnode);
            for (int i = 0; i < strs.length; ++i) {
                if (currentnode.checkBranch(strs[i]) != null) {
                    currentnode = currentnode.checkBranch(strs[i]);
                    queue.add(currentnode);
                } else {
                    break;
                }
            }
            delBranch(fruit_, queue);
            queue.clear();
        }

        strarray = fruit_.getPinYin();
        for (String str : strarray) {
            str = str.replaceAll(" ", "");
            str = StringMachine.insertBlank(str);
            String[] strs = str.split(" ");
            ACNode currentnode = this.root;
            queue.add(currentnode);
            for (int i = 0; i < strs.length; ++i) {
                if (currentnode.checkBranch(strs[i]) != null) {
                    currentnode = currentnode.checkBranch(strs[i]);
                    queue.add(currentnode);
                } else {
                    break;
                }
            }
            delBranch(fruit_, queue);
            queue.clear();
        }

        delCore(fruit_);
    }

    public void addBud() {
        ACNode currentnode = this.root;
        currentnode.addBud(null);

        Queue queue = new ArrayDeque<ACNode>();
        Iterator<String> iter = currentnode.branches.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            ACNode tmpnode = (ACNode) (currentnode.branches.get(key));
            tmpnode.addBud(currentnode);
            queue.add(tmpnode);
        }

        while ((currentnode = (ACNode) queue.poll()) != null) {
            // System.out.println(currentnode.leaf);
            iter = currentnode.branches.keySet().iterator();
            while (iter.hasNext()) {
                String key = iter.next();
                ACNode tmpnode = (ACNode) (currentnode.branches.get(key));
                addBud(tmpnode, currentnode);
                queue.add(tmpnode);
            }
        }
    }

    public void addBud(ACNode currentnode, ACNode parentnode) {
        ACNode budnode = parentnode.bud;
        String key = currentnode.getLeaf();
        while (budnode != null && budnode.checkBranch(key) == null) {
            budnode = budnode.bud;
        }
        if (budnode != null) {
            currentnode.addBud((ACNode) (budnode.branches.get(key)));
        } else {
            currentnode.addBud(this.root);
        }
        currentnode.addLink(currentnode.bud);
    }

    public List findBranch(String text) {
        String[] strs = text.split(" ");
        ACNode currentnode = this.root;
        List ret = new ArrayList<Match>();
        for (int i = 0; i < strs.length; ++i) {
            if (currentnode.checkBranch(strs[i]) != null) {
                currentnode = currentnode.checkBranch(strs[i]);
                for (Iterator<Info> iter = currentnode.fruits.iterator(); iter.hasNext();) {
                    Match match = new Match(i, currentnode.getLevel(), (Info) iter.next());
                    ret.add(match);
                }
                for (Iterator<ACNode> iter = currentnode.link.iterator(); iter.hasNext();) {
                    ACNode tmpnode = iter.next();
                    for (Iterator<Info> iter_ = tmpnode.fruits.iterator(); iter_.hasNext();) {
                        Match match = new Match(i, tmpnode.getLevel(), (Info) iter_.next());
                        ret.add(match);
                    }
                }
            } else if (currentnode.bud != null) {
                currentnode = currentnode.bud;
                i--;
            }
        }

        return ret;
    }
}
