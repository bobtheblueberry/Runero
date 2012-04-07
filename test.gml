
/* NOOO NOT THE SWITCH STATEMENT
switch (keyboard_key)
{
  case vk_left:
  case vk_numpad4:
    x -= 4; break;
  case vk_right:
  case vk_numpad6:
    x += 4; break;
}
*/


xy[i] = 2;



 x = y b = 2 n = 5 g = 2 var h i j ; a += b for (i = 0 i < 55 i += 1) x = 1 do y =x div 2 until i < 2 y = 2 x =
     show_message('bbbb')
    yx[55] = 2
do{a=(0).x+0end until"a"="b" // 123
// 123

{
va0123 = 2;
  for (i=0; i<=9; i+=1) list[iQC5] = i+1;
}

do x = 2 until y > 3 
y = 2
//for (i=0i<10i+=1)show_message(string(i))
with (all)
{
  if (distance_to_object(other) < 50) instance_destroy();
}
with (ball)
{
  x = other.x;
  y = other.y;
}
with (ball)
{
  x = random(room_width);
  y = random(room_height);
}



cheese.is.a.real.noob = 1
cheese.          is.  real . noob        =2
cheese.is.a[1].noob.b1234y[1]=2

x[1 + 2 / 5 % 6 ^ 3/*, 6 /2+round(2.5) + 2 + x[7]*/] = 1
y[200 /y - 2*2000] = 2 

// This scripts shows a message and pauses the game

{
  draw_set_font(score_font);
  draw_set_color(c_red);
  draw_set_halign(fa_center);
  draw_text(room_width/2,100,'Game Paused. Press any key to continue.');
  screen_refresh();
  keyboard_wait();
  io_clear();
}

////////////////////////////////////////////////////////////////
// script name: highscore_fill
//     creator: Mark Overmars
//        date: May 26, 2001
//
// description: This functions tries to fill the highscore list
//              with 10 starting values. (but only if it is empty)
//
//   arguments: argument 0: top score to use
//
//     remarks: Place in the Game Start event of some object.
////////////////////////////////////////////////////////////////
{
  // check whether highscore contains values
  if (highscore_value(1) > 0) exit;
  // fill in some nice names
  nnn = argument0/10;
  highscore_add('top shot'      ,10*nnn);
  highscore_add('excellent'     , 9*nnn);
  highscore_add('very good'     , 8*nnn);
  highscore_add('good'          , 7*nnn);
  highscore_add('reasonable'    , 6*nnn);
  highscore_add('average'       , 5*nnn);
  highscore_add('getting better', 4*nnn);
  highscore_add('poor'          , 3*nnn);
  highscore_add('very poor'     , 2*nnn);
  highscore_add('nothing'       , 1*nnn);
}
// This script (that is specific for this game) creates the
// different types of ufo's at reasonable moments.
// It is sort of the AI for the game.
{
  // depending on room, set the occurence frequency
  life_appear = 1000000; 
  ufo1_appear = 1000000;
  ufo2_appear = 1000000;
  ufo3_appear = 1000000;
  if (room == room0)
    { life_appear = 800;}
  else if (room == room1)
    { life_appear = 800; ufo1_appear = 600; }
  else if (room == room2)
    { life_appear = 700; ufo3_appear = 400; } 
  else if (room == room3)
    { life_appear = 600; ufo1_appear = 500; ufo2_appear = 900; }
  else if (room == room4)
    { life_appear = 600; ufo1_appear = 600; ufo2_appear = 700; ufo3_appear = 400}
  else 
    { life_appear = 600; ufo1_appear = 500; ufo2_appear = 500; ufo3_appear = 400}

  // increment the counters since last appearence
  life_counter += 1;     // time since last lifestar
  ufo1_counter += 1;     // time since last ufo1
  ufo2_counter += 1;     // time since last ufo2
  ufo3_counter += 1;     // time since last ufo3

  // now create them when required
  if (life_counter + random(life_appear/2) > life_appear && instance_number(rock_small)>1) 
    { instance_create(0,0,lifestar); life_counter = 0;}
  if (ufo1_counter + random(ufo1_appear/2) > ufo1_appear) 
    { instance_create(0,0,ufo1); ufo1_counter = 0;}
  if (ufo2_counter + random(ufo2_appear/2) > ufo2_appear) 
    { instance_create(0,0,ufo2); ufo2_counter = 0;}
  if (ufo3_counter + random(ufo3_appear/2) > ufo3_appear) 
    { instance_create(0,0,ufo3); ufo3_counter = 0;}
}
{
  // initialize the counters
  life_counter = 0;     // time since last lifestar
  ufo1_counter = 0;     // time since last ufo1
  ufo2_counter = 0;     // time since last ufo2
  ufo3_counter = 0;     // time since last ufo3
}
{
    var ahead, left, right;
    if ( !place_snapped(32,32) ) exit;
    if (vspeed == 0)
    {
        ahead = place_free(x+hspeed,y);
        left = place_free(x,y+4);
        right = place_free(x,y-4);
        if (!ahead && !left && !right) {direction += 180; exit;}
        while(true)   // forever
        {
            if (ahead && random(3)<1) {exit;}
            if (left && random(3)<1) {direction = 270; exit;}
            if (right && random(3)<1) {direction = 90; exit;}
        }
    }
    else
    {
        ahead = place_free(x,y+vspeed);
        left = place_free(x+4,y);
        right = place_free(x-4,y);
        if (!ahead && !left && !right) {vspeed = -vspeed; exit;}
        while(true)   // forever
        {
            if (ahead && random(3)<1) {exit;}
            if (left && random(3)<1) {direction = 0; exit;}
            if (right && random(3)<1) {direction = 180; exit;}
        }
    }
}
{
    if ( !place_snapped(32,32) ) exit;
    // find out in which direction the explorer is located
    var dir;
    dir = point_direction(x,y,obj_explorer.x,obj_explorer.y);
    dir = round(dir/90);
    if (dir == 4) dir = 0;
    // the four rules that move the mummy in the explorers direction
    if (dir == 0 && direction != 180 && place_free(x+4,y)) 
        { direction = 0; exit; }
    if (dir == 1 && direction != 270 && place_free(x,y-4)) 
        { direction = 90; exit; }
    if (dir == 2 && direction !=   0 && place_free(x-4,y)) 
        { direction = 180; exit; }
    if (dir == 3 && direction !=  90 && place_free(x,y+4)) 
        { direction = 270; exit; }
    // otherwise do the normal walking behavior
    scr_behavior_walk();
}
{	
    // Only draw if the explorer has found the sword
    if ( !obj_explorer.has_sword ) exit;
    // First determine the size of the lit area
    var x1,y1,x2,y2,ww;
    if ( global.swordon ) ww = 800 else ww = 300;
    x1 = argument0-ww/2;
    x2 = argument0+ww/2;
    y1 = argument1-ww/2;
    y2 = argument1+ww/2;
    // Hide things that are far away by drawing black rectangle
    draw_set_color(c_black);
    draw_rectangle(0,0,x1,room_height,false);
    draw_rectangle(x2,0, room_width,room_height,false);
    draw_rectangle(0,0,room_width,y1,false);
    draw_rectangle(0,y2,room_width,room_height,false);
    // Now hide nearby stuff by subtracting the light image
    draw_set_blend_mode(bm_subtract);
    draw_background_stretched(back_light,x1,y1,ww,ww);
    draw_set_blend_mode(bm_normal);
} 


{
  x = 23;
  color = $FFAA00;
  str = 'hello world';
  y += 5;
  x *= y;
  x = y << 2;
  x = 23*((2+4) / sin(y));
  str = 'hello' + " world";
  b = (x < 5) && !(x==2 || x==4);
}
// Josh is black
 if 0=="str"sound_play(0) 
//for({i=0; j=1;}; i<j; {i*=2; k+=3end) begin}