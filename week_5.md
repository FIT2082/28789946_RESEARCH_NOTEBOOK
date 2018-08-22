This week I continued developing the sqlite -> gentra4cp convertor.
To visualise the sqlite output and build a faithful convertor I installed **CP-Profiler** and **GECODE**.
GECODE is used to solve CSPs and **CP-Profiler** provides visual feedback. 

# Major dependencies
1. MiniZinc Compiler
2. Qt5

# installing Minizinc compiler
1. Download the bundled binary from http://www.minizinc.org/software.html
2. add Minizinc executables to path ```export PATH=$PATH:/Applications/MiniZincIDE.app/Contents/Resources/``` 


# buiding cpprofiler notes:
## installing qt5..
quite an annoying installation process
~~1. download online installer~~ Don't even try this.
1. on a mac ```brew install qt5```
2. then ```brew link qt5 --force``` for command line use
3. add qt5 to path with ```export PATH=/usr/local/opt/qt5/bin:$PATH```

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

Run fzn-gecode --help and make sure that CPProfiler support listed as enabled.
