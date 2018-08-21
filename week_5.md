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

