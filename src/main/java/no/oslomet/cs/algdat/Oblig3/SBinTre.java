package no.oslomet.cs.algdat.Oblig3;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

public class SBinTre<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public SBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    public boolean leggInn(T verdi) {
        requireNonNull(verdi, "Ulovlig med nullverdier!");
        Node<T> p = rot, q = null; // p starter i roten
        int cmp = 0;                // hjelpevariabel
        while (p != null)           // fortsetter til p er ute av treet
        {
            q = p; // q er forelder til p
            cmp = comp.compare(verdi, p.verdi); // bruker komparatoren
            p = cmp < 0 ? p.venstre : p.høyre; // flytter p
        }

        // p er nå null, dvs. ute av treet, q er den siste vi passerte

        p = new Node<T>(verdi, q); // oppretter en ny node

        if (q == null) rot = p; // p blir rotnode
        else if (cmp < 0) q.venstre = p; // venstre barn til q
        else q.høyre = p; // høyre barn til q
        antall++; // én verdi mer i treet
        return true; // vellykket innlegging
    }

    public boolean fjern(T verdi) {

        if (verdi == null) return false;  // treet har ingen nullverdier

        Node<T> p = rot, q = null;   // q skal være forelder til p

        while (p != null)            // leter etter verdi
        {
            int cmp = comp.compare(verdi, p.verdi);      // sammenligner
            if (cmp < 0) {
                q = p;
                p = p.venstre;
            }      // går til venstre
            else if (cmp > 0) {
                q = p;
                p = p.høyre;
            }   // går til høyre
            else break;    // den søkte verdien ligger i p
        }
        if (p == null) return false;   // finner ikke verdi

        if (p.venstre == null || p.høyre == null)  // Tilfelle 1) og 2)
        {
            Node<T> b = p.venstre != null ? p.venstre : p.høyre;  // b for barn
            if (p == rot) rot = b;
            else if (p == q.venstre) q.venstre = b;
            else q.høyre = b;
        } else  // Tilfelle 3)
        {
            Node<T> s = p, r = p.høyre;   // finner neste i inorden
            while (r.venstre != null) {
                s = r;    // s er forelder til r
                r = r.venstre;
            }

            p.verdi = r.verdi;   // kopierer verdien i r til p

            if (s != p) s.venstre = r.høyre;
            else s.høyre = r.høyre;
        }

        antall--;   // det er nå én node mindre i treet
        return true;
    }

    public int fjernAlle(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int antall(T verdi) {
        if (verdi == null) {
            return 0;
        }
        int teller = 0;
        Node<T> p = rot;
        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) {
                p = p.venstre;
            } else if (cmp > 0) {
                p = p.høyre;
            } else teller++;
        }
        return teller;
    }

    public void nullstill() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {

        if (p == null) throw new NoSuchElementException("Treet er tomt!");

        while (true) {
            if (p.venstre != null) p = p.venstre;
            else if (p.høyre != null) p = p.høyre;
            else return p;
        }
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {

        if (p == null) throw new NoSuchElementException("Treet er tomt!");

        if (p.forelder == null) {
            return null;
        } else if (p.forelder.venstre == p) {
            if (p.forelder.høyre == null) {
                return p.forelder;
            } else {
                boolean nesteFunnet = false;
                Node<T> neste = null;
                p = p.forelder.høyre;
                while (!nesteFunnet) {
                    if (p.venstre != null) {
                        p = p.venstre;

                    } else if (p.høyre != null) {
                        p = p.høyre;

                    } else {
                        nesteFunnet = true;
                        neste = p;
                    }
                }
                return neste;
            }
        } else return p.forelder;


        /*  Hvis p ikke har en forelder (p er rotnoden), så er p den siste i postorden.
            Hvis p er høyre barn til sin forelder f, er forelderen f den neste.
            Hvis p er venstre barn til sin forelder f, gjelder:

            Hvis p er enebarn (f.høyre er null), er forelderen f den neste.
            Hvis p ikke er enebarn (dvs. f.høyre er ikke null),
            så er den neste den noden som kommer først i postorden i subtreet med f.høyre som rot.
         */

    }

    public void postorden(Oppgave<? super T> oppgave) {
        Node<T> p = førstePostorden(rot);
        while (p != null) {
            oppgave.utførOppgave(p.verdi);
            p = nestePostorden(p);
        }
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {

        if (p == null) {
            return;
        }

        if (p.venstre != null) {
            postordenRecursive(p.venstre, oppgave);

        }
        if (p.høyre != null) {
            postordenRecursive(p.høyre, oppgave);
        }
        oppgave.utførOppgave(p.verdi);

        //System.out.printf("%s ", p.verdi);
            }



    public ArrayList<T> serialize() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    static <K> SBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public static <T> T requireNonNull(T obj, String s) {
        if (obj == null)
            throw new NullPointerException();
        return obj;
    }


} // ObligSBinTre