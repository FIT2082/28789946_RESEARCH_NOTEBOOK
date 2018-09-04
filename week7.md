# Week 7

Finished writing convertor for sqlite -> gentra4cp.

For interested parties this code is located in the convertor directory of this repo. 
Accessible [here](here).  

Below is an example of a full gentra4cp xml document which describes the execution of a CSP. 

```XML
<!--
DOCTYPE gentra4cp SYSTEM 
"http://contraintes.inria.fr/OADymPPaC/Public/Trace/gentra4cp.2.1.dtd"
-->
<gentra4cp>
<header>
<date>2004-04-28 01:53:56</date>
<source>sorted-gnu</source>
<creator>deransar</creator>
<contributor/>
<description/>
<rights>This trace is public domain.</rights>
<solver>
Codeine, Version 0.9.2 (2004-04-21). COnstraint DEbugging INteractive EnvironmentBy Ludovic Langevine and Tristan Denmat
</solver>
<parameters> multsorted(2,_15) </parameters>
</header>
<provide>...</provide>
<choice-point chrono="1" depth="0" nident="0" nname="root" time="0"/>
<new-variable chrono="2" depth="1" vident="v1" vinternal="_#3" vname="var2">
<vardomain min="1" max="3" size="3">
<range from="1" to="3"/>
</vardomain>
</new-variable>
<new-constraint chrono="3" depth="1" cident="c1" cinternal="fd_domain(v1,1,3)" orig="user" cname="fdom"/>
<post chrono="4" depth="1" cident="c1"/>
<solved chrono="5" depth="1" cident="c1"/>
<new-variable chrono="6" depth="1" vident="v2" vinternal="_#25" vname="var1">
<vardomain min="1" max="3" size="3">
<range from="1" to="3"/>
</vardomain>
</new-variable>
<new-constraint chrono="7" depth="1" cident="c2" cinternal="fd_domain(v2,1,3)" orig="user" cname="fdom"/>
<post chrono="8" depth="1" cident="c2"/>
<solved chrono="9" depth="1" cident="c2"/>
<choice-point chrono="10" depth="1" nident="1" time="0"/>
<new-constraint chrono="11" depth="2" cident="c3" cinternal="x_lt_y(v1,v2)" orig="user" cname="cons2"/>
<post chrono="12" depth="2" cident="c3"/>
<reduce chrono="13" depth="2" algo="initial[2]" cident="c3" vident="v1">...</reduce>
<reduce chrono="14" depth="2" algo="initial[2]" cident="c3" vident="v2">...</reduce>
<suspend chrono="15" depth="2" cident="c3"/>
<choice-point chrono="16" depth="2" nident="2" time="0"/>
<new-constraint chrono="17" depth="3" cident="c6" cinternal="assign(v1,1)" orig="system"/>
<post chrono="18" depth="3" cident="c6"/>
<reduce chrono="19" depth="3" algo="initial[1]" cident="c6" vident="v1">...</reduce>
<solved chrono="20" depth="3" cident="c6"/>
<choice-point chrono="21" depth="3" nident="3" time="0"/>
<new-constraint chrono="22" depth="4" cident="c7" cinternal="assign(v2,2)" orig="system"/>
<post chrono="23" depth="4" cident="c7"/>
<reduce chrono="24" depth="4" algo="initial[1]" cident="c7" vident="v2">
<delta>
<range from="3" to="3"/>
</delta>
<update vident="v2" types="max minmax dom val"/>
</reduce>
<solved chrono="25" depth="4" cident="c7"/>
<schedule chrono="26" depth="4" actions="dequeue">
<update vident="v2" types="max"/>
</schedule>
<solution chrono="27" depth="4" nident="4" time="0">
<state>
<variable vident="v1" type="int" vinternal="_#3" vname="var2">
<vardomain min="1" max="1" size="1">
<range from="1" to="1"/>
</vardomain>
</variable>
<variable vident="v2" type="int" vinternal="_#25" vname="var1">
<vardomain min="2" max="2" size="1">
<range from="2" to="2"/>
</vardomain>
</variable>
</state>
</solution>
<back-to chrono="28" depth="3" node="3" node-before="4" time="0"/>
<new-constraint chrono="29" depth="4" cident="c8" cinternal="assign(v2,3)" orig="system"/>
<post chrono="30" depth="4" cident="c8"/>
<reduce chrono="31" depth="4" algo="initial[1]" cident="c8" vident="v2">
<delta>
<range from="2" to="2"/>
</delta>
<update vident="v2" types="min minmax dom val"/>
</reduce>
<solved chrono="32" depth="4" cident="c8"/>
<solution chrono="33" depth="4" nident="5" time="0">
<state>
<variable vident="v1" type="int" vinternal="_#3" vname="var2">
<vardomain min="1" max="1" size="1">
<range from="1" to="1"/>
</vardomain>
</variable>
<variable vident="v2" type="int" vinternal="_#25" vname="var1">
<vardomain min="3" max="3" size="1">
<range from="3" to="3"/>
</vardomain>
</variable>
</state>
</solution>
<back-to chrono="34" depth="2" node="2" node-before="5" time="0"/>
<new-constraint chrono="35" depth="3" cident="c9" cinternal="assign(v1,2)" orig="system"/>
<post chrono="36" depth="3" cident="c9"/>
<reduce chrono="37" depth="3" algo="initial[1]" cident="c9" vident="v1">
<delta>
<range from="1" to="1"/>
</delta>
<update vident="v1" types="min minmax dom val"/>
</reduce>
<solved chrono="38" depth="3" cident="c9"/>
<schedule chrono="39" depth="3" actions="dequeue">
<update vident="v1" types="min"/>
</schedule>
<awake chrono="40" depth="3" cident="c3">
<update vident="v1" types="min"/>
</awake>
<reduce chrono="41" depth="3" algo="f_5[1]" cident="c3" vident="v2">
<delta>
<range from="2" to="2"/>
</delta>
<update vident="v2" types="min minmax dom val"/>
</reduce>
<suspend chrono="42" depth="3" cident="c3"/>
<solution chrono="43" depth="3" nident="6" time="0">
<state>
<variable vident="v1" type="int" vinternal="_#3" vname="var2">
<vardomain min="2" max="2" size="1">
<range from="2" to="2"/>
</vardomain>
</variable>
<variable vident="v2" type="int" vinternal="_#25" vname="var1">
<vardomain min="3" max="3" size="1">
<range from="3" to="3"/>
</vardomain>
</variable>
</state>
</solution>
<back-to chrono="44" depth="2" node="2" node-before="6" time="0"/>
<failure chrono="45" depth="3" nident="7" time="0"/>
<back-to chrono="46" depth="0" node="0" node-before="7" time="0"/>
</gentra4cp>
```

Gentra4cp XML is the input format for _CSP Singer_. The output is a midi disassembly. This format is basically a text file which can be converted into a [MIDI](http://www.indiana.edu/~emusic/361/midi.htm) file. This format is quite rare and can only be assembled into a MIDI file using [this](http://midi.teragonaudio.com/progs/software.htm) windows executable (no source code is available). 
This file can then be played (listened to) through an MIDI program such as GarageBand.

Below is the midi disassembly of the xml document. 
```
MThd | Format=1 | # of Tracks=4 | Division=192
Track #0
 |Time Sig |  4/4      | 
	    |Copyright	 | len=39   |
<Jeremie VAUTARD - Université d' Orléans>
1 : 1 : 0 |  Tempo | BPM=140 | 
1 : 1 : 0 |  Tempo | BPM=142 | 
1 : 3 : 0 |  Tempo | BPM=142 | 
1 : 3 : 0 |  Tempo | BPM=144 | 
1 : 3 : 0 |  Tempo | BPM=144 | 
1 : 3 : 0 |  Tempo | BPM=146 | 
1 : 3 : 0 |  Tempo | BPM=146 | 
1 : 3 : 0 |  Tempo | BPM=148 | 
2 : 1 : 0 |  Tempo | BPM=146 | 
2 : 1 : 0 |  Tempo | BPM=148 | 
3 : 1 : 0 |  Tempo | BPM=144 | 
3 : 1 : 0 |  Tempo | BPM=146 | 
4 : 1 : 0 |  Tempo | BPM=144 | 
4 : 1 : 0 |  Tempo | BPM=146 | 
4 : 1 : 0 |  Tempo | BPM=140 | 
4 : 1 : 0 |  Tempo | BPM=142 | 
4 : 1 : 0 |End of track|

Track #1
1 : 1 : 0 | Program | chan=1 | pgm #=1
           | on Note | chan=1 | pitch=E 3  | vol=64
1 : 1 : 64 | Program | chan=1 | pgm #=1
           | off Note | chan=1 | pitch=E 3  | vol=64
1 : 1 : 64 | Program | chan=1 | pgm #=1
           | on Note | chan=1 | pitch=F 3  | vol=64
1 : 1 : 128 | Program | chan=1 | pgm #=1
           | off Note | chan=1 | pitch=F 3  | vol=64
1 : 1 : 128 | Program | chan=1 | pgm #=1
           | on Note | chan=1 | pitch=G 3  | vol=64
1 : 2 : 0 | Program | chan=1 | pgm #=1
           | off Note | chan=1 | pitch=G 3  | vol=64
1 : 2 : 0 | Program | chan=1 | pgm #=1
           | on Note | chan=1 | pitch=E 3  | vol=64
1 : 2 : 64 | Program | chan=1 | pgm #=1
           | off Note | chan=1 | pitch=E 3  | vol=64
1 : 2 : 64 | Program | chan=1 | pgm #=1
           | on Note | chan=1 | pitch=F 3  | vol=64
1 : 2 : 128 | Program | chan=1 | pgm #=1
           | off Note | chan=1 | pitch=F 3  | vol=64
1 : 2 : 128 | Program | chan=1 | pgm #=1
           | on Note | chan=1 | pitch=G 3  | vol=64
1 : 3 : 0 | Program | chan=1 | pgm #=1
           | off Note | chan=1 | pitch=G 3  | vol=64
1 : 3 : 0 | Program | chan=1 | pgm #=1
           | on Note | chan=1 | pitch=A 3  | vol=64
1 : 4 : 0 | Program | chan=1 | pgm #=1
           | off Note | chan=1 | pitch=A 3  | vol=64
1 : 4 : 0 | Program | chan=1 | pgm #=1
           | on Note | chan=1 | pitch=A#3  | vol=64
2 : 1 : 0 | Program | chan=1 | pgm #=1
           | off Note | chan=1 | pitch=A#3  | vol=64
2 : 3 : 0 | Program | chan=1 | pgm #=1
           | on Note | chan=1 | pitch=F 3  | vol=64
2 : 4 : 0 | Program | chan=1 | pgm #=1
           | off Note | chan=1 | pitch=F 3  | vol=64
2 : 4 : 0 | Program | chan=1 | pgm #=1
           | on Note | chan=1 | pitch=G#3  | vol=64
3 : 1 : 0 | Program | chan=1 | pgm #=1
           | off Note | chan=1 | pitch=G#3  | vol=64
3 : 3 : 0 | Program | chan=1 | pgm #=1
           | on Note | chan=1 | pitch=G#3  | vol=64
3 : 4 : 0 | Program | chan=1 | pgm #=1
           | off Note | chan=1 | pitch=G#3  | vol=64
3 : 4 : 0 | Program | chan=1 | pgm #=1
           | on Note | chan=1 | pitch=A#3  | vol=64
4 : 1 : 0 | Program | chan=1 | pgm #=1
           | off Note | chan=1 | pitch=A#3  | vol=64
4 : 2 : 0 |End of track|

Track #2
1 : 1 : 0 | Program | chan=2 | pgm #=72
           | on Note | chan=2 | pitch=F 2  | vol=64
1 : 1 : 0 | Program | chan=2 | pgm #=72
           | on Note | chan=2 | pitch=A 2  | vol=64
1 : 1 : 0 | Program | chan=2 | pgm #=72
           | on Note | chan=2 | pitch=D 3  | vol=64
1 : 1 : 0 | Program | chan=2 | pgm #=72
           | on Note | chan=2 | pitch=F 3  | vol=64
1 : 2 : 0 | Program | chan=2 | pgm #=72
           | off Note | chan=2 | pitch=F 2  | vol=64
1 : 2 : 0 | Program | chan=2 | pgm #=72
           | off Note | chan=2 | pitch=A 2  | vol=64
1 : 2 : 0 | Program | chan=2 | pgm #=72
           | off Note | chan=2 | pitch=D 3  | vol=64
1 : 2 : 0 | Program | chan=2 | pgm #=72
           | off Note | chan=2 | pitch=F 3  | vol=64
1 : 2 : 0 | Program | chan=2 | pgm #=72
           | on Note | chan=2 | pitch=D 2  | vol=64
1 : 2 : 0 | Program | chan=2 | pgm #=72
           | on Note | chan=2 | pitch=F 2  | vol=64
1 : 2 : 0 | Program | chan=2 | pgm #=72
           | on Note | chan=2 | pitch=A 2  | vol=64
1 : 2 : 0 | Program | chan=2 | pgm #=72
           | on Note | chan=2 | pitch=D 3  | vol=64
1 : 2 : 0 | Program | chan=2 | pgm #=72
           | on Note | chan=2 | pitch=F 3  | vol=64
1 : 3 : 0 | Program | chan=2 | pgm #=72
           | off Note | chan=2 | pitch=D 2  | vol=64
1 : 3 : 0 | Program | chan=2 | pgm #=72
           | off Note | chan=2 | pitch=F 2  | vol=64
1 : 3 : 0 | Program | chan=2 | pgm #=72
           | off Note | chan=2 | pitch=A 2  | vol=64
1 : 3 : 0 | Program | chan=2 | pgm #=72
           | off Note | chan=2 | pitch=D 3  | vol=64
1 : 3 : 0 | Program | chan=2 | pgm #=72
           | off Note | chan=2 | pitch=F 3  | vol=64
2 : 1 : 0 | Program | chan=2 | pgm #=72
           | on Note | chan=2 | pitch=A#2  | vol=64
2 : 1 : 0 | Program | chan=2 | pgm #=72
           | on Note | chan=2 | pitch=D 3  | vol=64
2 : 1 : 0 | Program | chan=2 | pgm #=72
           | on Note | chan=2 | pitch=F 3  | vol=64
2 : 1 : 0 | Program | chan=2 | pgm #=72
           | on Note | chan=2 | pitch=A#3  | vol=64
2 : 1 : 0 | Program | chan=2 | pgm #=72
           | on Note | chan=2 | pitch=D 4  | vol=64
2 : 3 : 0 | Program | chan=2 | pgm #=72
           | off Note | chan=2 | pitch=A#2  | vol=64
2 : 3 : 0 | Program | chan=2 | pgm #=72
           | off Note | chan=2 | pitch=D 3  | vol=64
2 : 3 : 0 | Program | chan=2 | pgm #=72
           | off Note | chan=2 | pitch=F 3  | vol=64
2 : 3 : 0 | Program | chan=2 | pgm #=72
           | off Note | chan=2 | pitch=A#3  | vol=64
2 : 3 : 0 | Program | chan=2 | pgm #=72
           | off Note | chan=2 | pitch=D 4  | vol=64
3 : 1 : 0 | Program | chan=2 | pgm #=72
           | on Note | chan=2 | pitch=D#2  | vol=64
3 : 1 : 0 | Program | chan=2 | pgm #=72
           | on Note | chan=2 | pitch=G 2  | vol=64
3 : 1 : 0 | Program | chan=2 | pgm #=72
           | on Note | chan=2 | pitch=C 3  | vol=64
3 : 3 : 0 | Program | chan=2 | pgm #=72
           | off Note | chan=2 | pitch=D#2  | vol=64
3 : 3 : 0 | Program | chan=2 | pgm #=72
           | off Note | chan=2 | pitch=G 2  | vol=64
3 : 3 : 0 | Program | chan=2 | pgm #=72
           | off Note | chan=2 | pitch=C 3  | vol=64
4 : 2 : 0 |End of track|


Track #3
1: 1:  0  |Program  | chan=10   | pgm #=  1 Grand Piano
1 : 3 : 0 |  on Note | chan=10 | pitch=C#2  | vol=64
1 : 3 : 0 |  off Note | chan=10 | pitch=C#2  | vol=64
2 : 3 : 0 |  on Note | chan=10 | pitch=C#2  | vol=64
2 : 3 : 0 |  off Note | chan=10 | pitch=C#2  | vol=64
3 : 3 : 0 |  on Note | chan=10 | pitch=C#2  | vol=64
3 : 3 : 0 |  off Note | chan=10 | pitch=C#2  | vol=64
4 : 1 : 0 |  on Note | chan=10 | pitch=D 1  | vol=64
4 : 1 : 0 |  off Note | chan=10 | pitch=D 1  | vol=64
4 : 2 : 0 | End of Track|
```
The midi file can be found [here](linkhere).

---
## This week 
This week we will begin analysis of the produced sonified algorithms. This will be largely experimental and involve sonifiying search trees of similar classes of problems.
From this we will try and detect similarities and repetition in their musical structure. 
Some problems we will be looking at this week are:
1. golfing
2. symmetric problems

### A little on symmetry:
Symmetric problems are problems which are 