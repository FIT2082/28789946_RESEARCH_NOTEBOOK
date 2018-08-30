This week I continued developing the sqlite -> gentra4cp convertor.
To visualise the sqlite output and build a faithful convertor I installed **CP-Profiler** and **GECODE**.
GECODE is used to solve CSPs and **CP-Profiler** provides visual feedback. 

# Major dependencies
1. MiniZinc Compiler
2. Qt5

# installing Minizinc compiler
1. Download the bundled binary from http://www.minizinc.org/software.html
2. add Minizinc executables to path ```echo export PATH=$PATH:/Applications/MiniZincIDE.app/Contents/Resources/ >> ~/.zshrc``` 


# buiding cpprofiler notes:
## installing qt5..
quite an annoying installation process
~~1. download online installer~~ Don't even try this.
1. on a mac ```brew install qt5```
2. then ```brew link qt5 --force``` for command line use
3. add qt5 to path with ```echo export PATH=/usr/local/opt/qt5/bin:$PATH >> ~/.zshrc```

## github submodule ssh key issues
1. modify ```.gitmodules``` to https://github.com/cp-profiler/cpp-integration.git
2. run ```git submodule sync```
3. run ```git submodule update --init --recursive```


# building gecode notes
1. ```git clone git@github.com:Gecode/gecode.git```
2. ```cd gecode```
3. ```git checkout develop ``` # Do not forget this step
4. ```mkdir build```
5. ```cd build```
6. ```cmake -DENABLE_CPPROFILER=ON ..```
7. ```make```
8. Run ```fzn-gecode --help``` and make sure that CPProfiler support listed as enabled.

---
- During this week's meeting Kevin, Guido and I explored an example Gentra4CP xml file and discussed the process of converting the database files.
- Guido found some sample execution traces [here]
- The main xml elements we need to represent are:
1. choicepoint - denoting a branch
2. new constraint - the detail of the constraint, for example assignment.
3. failure - no solution found

- Some GECODE compilation issues were also solved during this meeting

##### Note I use [zsh](http://www.zsh.org/) shell so all commands reflect this. If you use bash replace with ```~/.bashrc```
