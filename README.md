# Obligatorisk oppgave 3 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende student:
* Olav Elnan, S236370, s236370@oslomet.no


# Oppgavebeskrivelse

I oppgave 1 så brukte jeg programkoden fra 5.2.3a i kompendiet. Det eneste den manglet var en foreldrereferanse 'q',
i noden. La derfor til den.

I oppgave 2 sjekkes det først om verdien er i treet, hvis ikke returneres 0. Deretter opprettes hjelpevariabelen 'teller',
som oppdateres hver gang den innsendte verdien blir funnet.
I metoden antall() fant jeg ut at ville bli løst mye lettere om jeg kalte på nestePostorden. Bruker derfor den til
å traversere treet.

I oppgave 3 finner vi metodene førstePostorden() og nestePostorden(). førstePostordenm() sjekker først venstre node,
så høyre node. Om ikke de blir funnet, returneres p/roten.

nestePostorden() har litt flere tilfeller. Hvis p ikke har en forelder, så er p den siste i postorden, dermed returneres null.
Hvis p er høyre barn til sin forelder, så er forelder den neste. Hvis p er venstre barn til forelder, så må programmet finne neste
ved å sjekke om p sin forelder har et høyre barn. Deretter sjekkes det etter barn med prioriteringsrekkefølge venstre, så høyre.
Det fortsetter fram til den neste i postorden er funnet.

I oppgave 4 brukes rekursive metoder. i postorden() kaller metoden på seg selv fram til p sin plass er funnet, dvs p ikke er null.
I postordenRecursive traverseres treet i postorden. Så lenge det finnes noe i venstre barn, prioriteres det først, deretter høyrebarn.

I Oppgave 5, i metoden serialize(), lager jeg en hjelpearray kalt kø før nodene blir lagt til i det endelige arrayet, kalt resultat.
Bruker kø.remove for å finne og fjerne noden samtidig. 
I deserialize() går jeg gjennom alle data i innsendte ArrayList også opprettes et SBinTre for å lagre resultatet.


