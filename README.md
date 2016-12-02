Urinal solver
===

Sometimes you want to find the best place to tinkle.  This is a simple 
urinal solver that uses a weighting strategy with rules 
to select which urinal would be the best one to wizz at.

It tries to prioritize corners, equidistance from other occupied stalls,
and minimizes sequential clusters of occuped stalls on either side. It also 
prioritizes being closer to a wall even if that means there are others 
more close by since a wall gives you more privacy

As an example, you can see how it selected the stall with the following example configuratinos:

```
 !  o  x  o  o  x 
 x  o  o  x  o  ! 
 x  o  !  o  x  o  x 
 x  o  o  o  x  o  ! 
 x  !  x  x 
 x  x  o  x  o  x  !  x
```

Where `x` is occupied, `o` is open, and `!` is the chosen stall