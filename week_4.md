Begun sqllite -> gentra4cp

Developing a converter from sqlite database files to gentra4cp format4. This was done by following the DTD of the gentra4cp xml format.


# project repo
https://github.com/gh4n/minizinger

# gentracp docs
http://pauillac.inria.fr/~contraintes/OADymPPaC/Net/tra4cp/www/GenTra4CP/FDSTraces/indexFDST.html
http://pauillac.inria.fr/~contraintes/OADymPPaC/Net/tra4cp/www/GenTra4CP/FDSTraces/newtrace_format.2.1.pdf

# links
https://arxiv.org/pdf/1105.6210.pdf
https://nanopdf.com/download/gentra4cp-a-generic-trace-format-for-constraint-programming_pdf
https://lallouet.users.greyc.fr/Publications_files/icmc06.pdf
http://ialab.it.monash.edu/~dwyer/papers/cpprofiling.pdf

# notes for final paper
## On gentra4cp
- DTD extemely convoluted and dense. Papers have criticized gentra4cp format for being too complex.
- Gentra4cp xml describes a ```trace``` object. The document consists of a prologue which identifies the (```<header>```element). 
- An element characterizing the set of trace events and attributes which will be encountered in the trace document (<provide> element). This element is normally required. If it is not given, the prologue must contain a <provide> element in the prologue.
Below is an example of a trace document:
```
<gentra4cp>
<header>
<date>2004-02-04 10:30:00</date> <source>queen-gnu.pl</source> <provide>
    <!-- ...-->
  </provide>
</header>
<provide>
    <!-- ...-->
</provide>
<packet control="breakpoint">
<new-variable chrono="1" vident="1" vname="x">
<vardomain> <values>1 2 3</values> </vardomain> </new-variable> <new-variable chrono="2" vident="2" vname="y">
<vardomain> <values>1 2 3</values> </vardomain> </new-variable> <!-- ...-->
<new-constraint chrono="5" cident="4" cname="c1" cexternal="X ## Y"> <update vident="1" types="ground min"/>
<update vident="y" types="ground max"/>
   </new-constraint>
    <!-- ...-->
</packet>
<!-- ...-->
</gentra4cp>```

