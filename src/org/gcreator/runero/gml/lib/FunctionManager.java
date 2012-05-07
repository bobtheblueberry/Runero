package org.gcreator.runero.gml.lib;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.gcreator.runero.RuneroGame;
import org.gcreator.runero.gml.Constant;
import org.lateralgm.file.gc.StreamDecoder;

public class FunctionManager {

    private FunctionManager()
        {
        }

    public static String[] ids;

    public static void load() {
        try {
            InputStream s;
            try {
                s = new FileInputStream("fnames");
            } catch (FileNotFoundException exc) {
                s = FunctionManager.class.getResourceAsStream("/fnames");
            }
            StreamDecoder in = new StreamDecoder(s);
            int num = in.read4();
            ids = new String[num];
            System.out.println("Functions: " + num);
            for (int i = 0; i < num; i++)
                ids[i] = in.readStr();
            in.close();
        } catch (IOException e) {
            System.out.println("Error loading function names");
            e.printStackTrace();
        }

    }

    public static int getId(String name) {
        for (int i = 0; i < ids.length; i++)
            if (ids[i].equals(name))
                return i;

        return -1;
    }

    public static Constant getFunction(int id, Constant... args) {

        Constant arg0 = null, arg1 = null, arg2 = null, arg3 = null, arg4 = null, arg5 = null;
        if (args.length > 0) {
            arg0 = args[0];
            if (args.length > 1) {
                arg1 = args[1];
                if (args.length > 2) {
                    arg2 = args[2];
                    if (args.length > 3) {
                        arg3 = args[3];
                        if (args.length > 4) {
                            arg4 = args[4];
                            if (args.length > 5)
                                arg5 = args[5];
                        }
                    }
                }
            }
        }

        switch (id) {
            case 0: // is_real(val)
                return new Constant(MathLibrary.is_real(arg0));
            case 1: // is_string(val)
                return new Constant(MathLibrary.is_string(arg0));
            case 2: // random(x)
                checkReal(arg0);
                return new Constant(MathLibrary.random(arg0.dVal));
            case 3: // random_range(x1,x2)
                checkReal(arg0);
                checkReal(arg1);
                return new Constant(MathLibrary.random_range(arg0.dVal, arg1.dVal));
            case 4: // irandom(x)
                checkReal(arg0);
                return new Constant(MathLibrary.irandom(arg0.dVal));
            case 5: // irandom_range(x1,x2)
                checkReal(arg0);
                checkReal(arg1);
                return new Constant(MathLibrary.irandom_range(arg0.dVal, arg1.dVal));
            case 6: // random_set_seed(seed)
                checkReal(arg0);
                MathLibrary.random_set_seed(Double.doubleToLongBits(arg0.dVal));
                return new Constant(0);
            case 7: // random_get_seed()
                return new Constant(Double.longBitsToDouble(MathLibrary.random_get_seed()));
            case 8: // randomize()
                MathLibrary.randomize();
                return new Constant(0);
            case 9: // choose(x1,x2,x3,...)
                return MathLibrary.choose(args);
            case 10: // abs(x)
                return new Constant(MathLibrary.abs(arg0.dVal));
            case 11: // round(x)
                return new Constant(MathLibrary.round(arg0.dVal));
            case 12: // floor(x)
                return new Constant(MathLibrary.floor(arg0.dVal));
            case 13: // ceil(x)
                return new Constant(MathLibrary.ceil(arg0.dVal));
            case 14: // sign(x)
                return new Constant(MathLibrary.sign(arg0.dVal));
            case 15: // frac(x)
                return new Constant(MathLibrary.frac(arg0.dVal));
            case 16: // sqrt(x)
                return new Constant(MathLibrary.sqrt(arg0.dVal));
            case 17: // sqr(x)
                return new Constant(MathLibrary.sqr(arg0.dVal));
            case 18: // exp(x)
                return new Constant(MathLibrary.exp(arg0.dVal));
            case 19: // ln(x)
                return new Constant(MathLibrary.ln(arg0.dVal));
            case 20: // log2(x)
                return new Constant(MathLibrary.log2(arg0.dVal));
            case 21: // log10(x)
                return new Constant(MathLibrary.log10(arg0.dVal));
            case 22: // sin(x)
                return new Constant(MathLibrary.sin(arg0.dVal));
            case 23: // cos(x)
                return new Constant(MathLibrary.cos(arg0.dVal));
            case 24: // tan(x)
                return new Constant(MathLibrary.tan(arg0.dVal));
            case 25: // arcsin(x)
                return new Constant(MathLibrary.arcsin(arg0.dVal));
            case 26: // arccos(x)
                return new Constant(MathLibrary.arccos(arg0.dVal));
            case 27: // arctan(x)
                return new Constant(MathLibrary.arctan(arg0.dVal));
            case 28: // arctan2(y,x)
                return new Constant(MathLibrary.arctan(arg0.dVal));
            case 29: // degtorad(x)
                return new Constant(MathLibrary.degtorad(arg0.dVal));
            case 30: // radtodeg(x)
                return new Constant(MathLibrary.radtodeg(arg0.dVal));
            case 31: // power(x,n)
                return new Constant(MathLibrary.power(arg0.dVal, arg1.dVal));
            case 32: // logn(n,x)
                return new Constant(MathLibrary.logn(arg0.dVal, arg1.dVal));
            case 33: // min(x1,x2,x3,...)
                return MathLibrary.min(args);
            case 34: // max(x1,x2,x3,...)
                return MathLibrary.max(args);
            case 35: // mean(x1,x2,x3,...)
                return new Constant(MathLibrary.mean(getDoubles(args)));
            case 36: // median(x1,x2,x3,...)
                return new Constant(MathLibrary.median(getDoubles(args)));
            case 37: // clamp(val,min,max)
                return new Constant(MathLibrary.clamp(arg0.dVal, arg1.dVal, arg2.dVal));
            case 38: // lerp(val1,val2,amount)
                return new Constant(MathLibrary.lerp(arg0.dVal, arg1.dVal, arg2.dVal));
            case 39: // dot_product(x1,y1,x2,y2)
                return new Constant(MathLibrary.dot_product(arg0.dVal, arg1.dVal, arg2.dVal, arg3.dVal));
            case 40: // dot_product_3d(x1,y1,z1,x2,y2,z2)
                return new Constant(MathLibrary.dot_product_3d(arg0.dVal, arg1.dVal, arg2.dVal, arg3.dVal, arg4.dVal,
                        arg5.dVal));
            case 41: // point_distance_3d(x1,y1,z1,x2,y2,z2)
                return new Constant(MathLibrary.point_distance_3d(arg0.dVal, arg1.dVal, arg2.dVal, arg3.dVal,
                        arg4.dVal, arg5.dVal));
            case 42: // point_distance(x1,y1,x2,y2)
                return new Constant(MathLibrary.point_distance(arg0.dVal, arg1.dVal, arg2.dVal, arg3.dVal));
            case 43: // point_direction(x1,y1,x2,y2)
                return new Constant(MathLibrary.point_direction(arg0.dVal, arg1.dVal, arg2.dVal, arg3.dVal));
            case 44: // lengthdir_x(len,dir)
                return new Constant(MathLibrary.lendir_x(arg0.dVal, arg1.dVal));
            case 45: // lengthdir_y(len,dir)
                return new Constant(MathLibrary.lendir_y(arg0.dVal, arg1.dVal));
            case 46: // real(str)
                return new Constant(StringLibrary.real(arg0.sVal));
            case 47: // string(val)
                return new Constant(StringLibrary.string(arg0));
            case 48: // string_format(val,total,dec)
                return new Constant(StringLibrary.string_format(arg0.dVal, (int) arg1.dVal, (int) arg2.dVal));
            case 49: // chr(val)
                return new Constant(StringLibrary.chr((int) arg0.dVal));
            case 50: // ansi_char(val)
                return new Constant(StringLibrary.ansi_char((int) arg0.dVal));
            case 51: // ord(char)
                return new Constant(StringLibrary.ord(arg0.sVal));
            case 52: // string_length(str)

                // TODO: This function

                return null; // string_length
            case 53: // string_pos(substr,str)

                // TODO: This function

                return null; // string_pos
            case 54: // string_copy(str,index,count)

                // TODO: This function

                return null; // string_copy
            case 55: // string_char_at(str,index)

                // TODO: This function

                return null; // string_char_at
            case 56: // string_delete(str,index,count)

                // TODO: This function

                return null; // string_delete
            case 57: // string_insert(substr,str,index)

                // TODO: This function

                return null; // string_insert
            case 58: // string_lower(str)

                // TODO: This function

                return null; // string_lower
            case 59: // string_upper(str)

                // TODO: This function

                return null; // string_upper
            case 60: // string_repeat(str,count)

                // TODO: This function

                return null; // string_repeat
            case 61: // string_letters(str)

                // TODO: This function

                return null; // string_letters
            case 62: // string_digits(str)

                // TODO: This function

                return null; // string_digits
            case 63: // string_lettersdigits(str)

                // TODO: This function

                return null; // string_lettersdigits
            case 64: // string_replace(str,substr,newstr)

                // TODO: This function

                return null; // string_replace
            case 65: // string_replace_all(str,substr,newstr)

                // TODO: This function

                return null; // string_replace_all
            case 66: // string_count(substr,str)

                // TODO: This function

                return null; // string_count
            case 67: // clipboard_has_text()

                // TODO: This function

                return null; // clipboard_has_text
            case 68: // clipboard_set_text(str)

                // TODO: This function

                return null; // clipboard_set_text
            case 69: // clipboard_get_text()

                // TODO: This function

                return null; // clipboard_get_text
            case 70: // date_current_datetime()

                // TODO: This function

                return null; // date_current_datetime
            case 71: // date_current_date()

                // TODO: This function

                return null; // date_current_date
            case 72: // date_current_time()

                // TODO: This function

                return null; // date_current_time
            case 73: // date_create_datetime(year,month,day,hour,minute,second)

                // TODO: This function

                return null; // date_create_datetime
            case 74: // date_create_date(year,month,day)

                // TODO: This function

                return null; // date_create_date
            case 75: // date_create_time(hour,minute,second)

                // TODO: This function

                return null; // date_create_time
            case 76: // date_valid_datetime(year,month,day,hour,minute,second)

                // TODO: This function

                return null; // date_valid_datetime
            case 77: // date_valid_date(year,month,day)

                // TODO: This function

                return null; // date_valid_date
            case 78: // date_valid_time(hour,minute,second)

                // TODO: This function

                return null; // date_valid_time
            case 79: // date_inc_year(date,amount)

                // TODO: This function

                return null; // date_inc_year
            case 80: // date_inc_month(date,amount)

                // TODO: This function

                return null; // date_inc_month
            case 81: // date_inc_week(date,amount)

                // TODO: This function

                return null; // date_inc_week
            case 82: // date_inc_day(date,amount)

                // TODO: This function

                return null; // date_inc_day
            case 83: // date_inc_hour(date,amount)

                // TODO: This function

                return null; // date_inc_hour
            case 84: // date_inc_minute(date,amount)

                // TODO: This function

                return null; // date_inc_minute
            case 85: // date_inc_second(date,amount)

                // TODO: This function

                return null; // date_inc_second
            case 86: // date_get_year(date)

                // TODO: This function

                return null; // date_get_year
            case 87: // date_get_month(date)

                // TODO: This function

                return null; // date_get_month
            case 88: // date_get_week(date)

                // TODO: This function

                return null; // date_get_week
            case 89: // date_get_day(date)

                // TODO: This function

                return null; // date_get_day
            case 90: // date_get_hour(date)

                // TODO: This function

                return null; // date_get_hour
            case 91: // date_get_minute(date)

                // TODO: This function

                return null; // date_get_minute
            case 92: // date_get_second(date)

                // TODO: This function

                return null; // date_get_second
            case 93: // date_get_weekday(date)

                // TODO: This function

                return null; // date_get_weekday
            case 94: // date_get_day_of_year(date)

                // TODO: This function

                return null; // date_get_day_of_year
            case 95: // date_get_hour_of_year(date)

                // TODO: This function

                return null; // date_get_hour_of_year
            case 96: // date_get_minute_of_year(date)

                // TODO: This function

                return null; // date_get_minute_of_year
            case 97: // date_get_second_of_year(date)

                // TODO: This function

                return null; // date_get_second_of_year
            case 98: // date_year_span(date1,date2)

                // TODO: This function

                return null; // date_year_span
            case 99: // date_month_span(date1,date2)

                // TODO: This function

                return null; // date_month_span
            case 100: // date_week_span(date1,date2)

                // TODO: This function

                return null; // date_week_span
            case 101: // date_day_span(date1,date2)

                // TODO: This function

                return null; // date_day_span
            case 102: // date_hour_span(date1,date2)

                // TODO: This function

                return null; // date_hour_span
            case 103: // date_minute_span(date1,date2)

                // TODO: This function

                return null; // date_minute_span
            case 104: // date_second_span(date1,date2)

                // TODO: This function

                return null; // date_second_span
            case 105: // date_compare_datetime(date1,date2)

                // TODO: This function

                return null; // date_compare_datetime
            case 106: // date_compare_date(date1,date2)

                // TODO: This function

                return null; // date_compare_date
            case 107: // date_compare_time(date1,date2)

                // TODO: This function

                return null; // date_compare_time
            case 108: // date_date_of(date)

                // TODO: This function

                return null; // date_date_of
            case 109: // date_time_of(date)

                // TODO: This function

                return null; // date_time_of
            case 110: // date_datetime_string(date)

                // TODO: This function

                return null; // date_datetime_string
            case 111: // date_date_string(date)

                // TODO: This function

                return null; // date_date_string
            case 112: // date_time_string(date)

                // TODO: This function

                return null; // date_time_string
            case 113: // date_days_in_month(date)

                // TODO: This function

                return null; // date_days_in_month
            case 114: // date_days_in_year(date)

                // TODO: This function

                return null; // date_days_in_year
            case 115: // date_leap_year(date)

                // TODO: This function

                return null; // date_leap_year
            case 116: // date_is_today(date)

                // TODO: This function

                return null; // date_is_today
            case 117: // motion_set(dir,speed)

                // TODO: This function

                return null; // motion_set
            case 118: // motion_add(dir,speed)

                // TODO: This function

                return null; // motion_add
            case 119: // place_free(x,y)

                // TODO: This function

                return null; // place_free
            case 120: // place_empty(x,y)

                // TODO: This function

                return null; // place_empty
            case 121: // place_meeting(x,y,obj)

                // TODO: This function

                return null; // place_meeting
            case 122: // place_snapped(hsnap,vsnap)

                // TODO: This function

                return null; // place_snapped
            case 123: // move_random(hsnap,vsnap)

                // TODO: This function

                return null; // move_random
            case 124: // move_snap(hsnap,vsnap)

                // TODO: This function

                return null; // move_snap
            case 125: // move_towards_point(x,y,sp)

                // TODO: This function

                return null; // move_towards_point
            case 126: // move_contact_solid(dir,maxdist)

                // TODO: This function

                return null; // move_contact_solid
            case 127: // move_contact_all(dir,maxdist)

                // TODO: This function

                return null; // move_contact_all
            case 128: // move_outside_solid(dir,maxdist)

                // TODO: This function

                return null; // move_outside_solid
            case 129: // move_outside_all(dir,maxdist)

                // TODO: This function

                return null; // move_outside_all
            case 130: // move_bounce_solid(advanced)

                // TODO: This function

                return null; // move_bounce_solid
            case 131: // move_bounce_all(advanced)

                // TODO: This function

                return null; // move_bounce_all
            case 132: // move_wrap(hor,vert,margin)

                // TODO: This function

                return null; // move_wrap
            case 133: // distance_to_point(x,y)

                // TODO: This function

                return null; // distance_to_point
            case 134: // distance_to_object(obj)

                // TODO: This function

                return null; // distance_to_object
            case 135: // position_empty(x,y)

                // TODO: This function

                return null; // position_empty
            case 136: // position_meeting(x,y,obj)

                // TODO: This function

                return null; // position_meeting
            case 137: // path_start(path,speed,endaction,absolute)

                // TODO: This function

                return null; // path_start
            case 138: // path_end()

                // TODO: This function

                return null; // path_end
            case 139: // mp_linear_step(x,y,speed,checkall)

                // TODO: This function

                return null; // mp_linear_step
            case 140: // mp_potential_step(x,y,speed,checkall)

                // TODO: This function

                return null; // mp_potential_step
            case 141: // mp_linear_step_object(x,y,speed,obj)

                // TODO: This function

                return null; // mp_linear_step_object
            case 142: // mp_potential_step_object(x,y,speed,obj)

                // TODO: This function

                return null; // mp_potential_step_object
            case 143: // mp_potential_settings(maxrot,rotstep,ahead,onspot)

                // TODO: This function

                return null; // mp_potential_settings
            case 144: // mp_linear_path(path,xg,yg,stepsize,checkall)

                // TODO: This function

                return null; // mp_linear_path
            case 145: // mp_potential_path(path,xg,yg,stepsize,factor,checkall)

                // TODO: This function

                return null; // mp_potential_path
            case 146: // mp_linear_path_object(path,xg,yg,stepsize,obj)

                // TODO: This function

                return null; // mp_linear_path_object
            case 147: // mp_potential_path_object(path,xg,yg,stepsize,factor,obj)

                // TODO: This function

                return null; // mp_potential_path_object
            case 148: // mp_grid_create(left,top,hcells,vcells,cellwidth,cellheight)

                // TODO: This function

                return null; // mp_grid_create
            case 149: // mp_grid_destroy(id)

                // TODO: This function

                return null; // mp_grid_destroy
            case 150: // mp_grid_clear_all(id)

                // TODO: This function

                return null; // mp_grid_clear_all
            case 151: // mp_grid_clear_cell(id,h,v)

                // TODO: This function

                return null; // mp_grid_clear_cell
            case 152: // mp_grid_clear_rectangle(id,left,top,right,bottom)

                // TODO: This function

                return null; // mp_grid_clear_rectangle
            case 153: // mp_grid_add_cell(id,h,v)

                // TODO: This function

                return null; // mp_grid_add_cell
            case 154: // mp_grid_add_rectangle(id,left,top,right,bottom)

                // TODO: This function

                return null; // mp_grid_add_rectangle
            case 155: // mp_grid_add_instances(id,obj,prec)

                // TODO: This function

                return null; // mp_grid_add_instances
            case 156: // mp_grid_path(id,path,xstart,ystart,xgoal,ygoal,allowdiag)

                // TODO: This function

                return null; // mp_grid_path
            case 157: // mp_grid_draw(id)

                // TODO: This function

                return null; // mp_grid_draw
            case 158: // collision_point(x,y,obj,prec,notme)

                // TODO: This function

                return null; // collision_point
            case 159: // collision_rectangle(x1,y1,x2,y2,obj,prec,notme)

                // TODO: This function

                return null; // collision_rectangle
            case 160: // collision_circle(x1,y1,radius,obj,prec,notme)

                // TODO: This function

                return null; // collision_circle
            case 161: // collision_ellipse(x1,y1,x2,y2,obj,prec,notme)

                // TODO: This function

                return null; // collision_ellipse
            case 162: // collision_line(x1,y1,x2,y2,obj,prec,notme)

                // TODO: This function

                return null; // collision_line
            case 163: // instance_find(obj,n)

                // TODO: This function

                return null; // instance_find
            case 164: // instance_exists(obj)

                // TODO: This function

                return null; // instance_exists
            case 165: // instance_number(obj)

                // TODO: This function

                return null; // instance_number
            case 166: // instance_position(x,y,obj)

                // TODO: This function

                return null; // instance_position
            case 167: // instance_nearest(x,y,obj)

                // TODO: This function

                return null; // instance_nearest
            case 168: // instance_furthest(x,y,obj)

                // TODO: This function

                return null; // instance_furthest
            case 169: // instance_place(x,y,obj)

                // TODO: This function

                return null; // instance_place
            case 170: // instance_create(x,y,obj)

                // TODO: This function

                return null; // instance_create
            case 171: // instance_copy(performevent)

                // TODO: This function

                return null; // instance_copy
            case 172: // instance_change(obj,performevents)

                // TODO: This function

                return null; // instance_change
            case 173: // instance_destroy()

                // TODO: This function

                return null; // instance_destroy
            case 174: // position_destroy(x,y)

                // TODO: This function

                return null; // position_destroy
            case 175: // position_change(x,y,obj,performevents)

                // TODO: This function

                return null; // position_change
            case 176: // instance_deactivate_all(notme)

                // TODO: This function

                return null; // instance_deactivate_all
            case 177: // instance_deactivate_object(obj)

                // TODO: This function

                return null; // instance_deactivate_object
            case 178: // instance_deactivate_region(left,top,width,height,inside,notme)

                // TODO: This function

                return null; // instance_deactivate_region
            case 179: // instance_activate_all()

                // TODO: This function

                return null; // instance_activate_all
            case 180: // instance_activate_object(obj)

                // TODO: This function

                return null; // instance_activate_object
            case 181: // instance_activate_region(left,top,width,height,inside)

                // TODO: This function

                return null; // instance_activate_region
            case 182: // sleep(millisec)
                checkReal(arg0);
                try {
                    Thread.sleep((long)arg0.dVal);
                } catch (InterruptedException e) {
                }
            case 183: // room_goto(numb)
                checkReal(arg0);
                RoomLibrary.room_goto((int) arg0.dVal, RuneroGame.game.transition_kind);
                RuneroGame.game.transition_kind = 0;
                return Constant.ZERO;
            case 184: // room_goto_previous()
                RoomLibrary.room_goto_previous(RuneroGame.game.transition_kind);
                RuneroGame.game.transition_kind = 0;
                return Constant.ZERO;
            case 185: // room_goto_next()
                RoomLibrary.room_goto_next(RuneroGame.game.transition_kind);
                RuneroGame.game.transition_kind = 0;
                return Constant.ZERO;
            case 186: // room_previous(numb)
                checkReal(arg0);
                return new Constant(RoomLibrary.room_previous((int) arg0.dVal));
            case 187: // room_next(numb)
                checkReal(arg0);
                return new Constant(RoomLibrary.room_next((int) arg0.dVal));
            case 188: // room_restart()
                RoomLibrary.room_restart(RuneroGame.game.transition_kind);
                RuneroGame.game.transition_kind = 0;
                return Constant.ZERO;
            case 189: // game_end()

                // TODO: This function
                System.exit(0);
                return null; // game_end
            case 190: // game_restart()

                // TODO: This function

                return null; // game_restart
            case 191: // game_load(filename)

                // TODO: This function

                return null; // game_load
            case 192: // game_save(filename)

                // TODO: This function

                return null; // game_save
            case 193: // transition_define(kind,name)

                // TODO: This function

                return null; // transition_define
            case 194: // transition_exists(kind)

                // TODO: This function

                return null; // transition_exists
            case 195: // event_perform(type,numb)

                // TODO: This function

                return null; // event_perform
            case 196: // event_user(numb)

                // TODO: This function

                return null; // event_user
            case 197: // event_perform_object(obj,type,numb)

                // TODO: This function

                return null; // event_perform_object
            case 198: // event_inherited()

                // TODO: This function

                return null; // event_inherited
            case 199: // show_debug_message(str)
                checkString(arg0);
                // TODO: This function

                return null; // show_debug_message
            case 200: // variable_global_exists(name)

                // TODO: This function

                return null; // variable_global_exists
            case 201: // variable_global_get(name)

                // TODO: This function

                return null; // variable_global_get
            case 202: // variable_global_array_get(name,ind)

                // TODO: This function

                return null; // variable_global_array_get
            case 203: // variable_global_array2_get(name,ind1,ind2)

                // TODO: This function

                return null; // variable_global_array2_get
            case 204: // variable_global_set(name,value)

                // TODO: This function

                return null; // variable_global_set
            case 205: // variable_global_array_set(name,ind,value)

                // TODO: This function

                return null; // variable_global_array_set
            case 206: // variable_global_array2_set(name,ind1,ind2,value)

                // TODO: This function

                return null; // variable_global_array2_set
            case 207: // variable_local_exists(name)

                // TODO: This function

                return null; // variable_local_exists
            case 208: // variable_local_get(name)

                // TODO: This function

                return null; // variable_local_get
            case 209: // variable_local_array_get(name,ind)

                // TODO: This function

                return null; // variable_local_array_get
            case 210: // variable_local_array2_get(name,ind1,ind2)

                // TODO: This function

                return null; // variable_local_array2_get
            case 211: // variable_local_set(name,value)

                // TODO: This function

                return null; // variable_local_set
            case 212: // variable_local_array_set(name,ind,value)

                // TODO: This function

                return null; // variable_local_array_set
            case 213: // variable_local_array2_set(name,ind1,ind2,value)

                // TODO: This function

                return null; // variable_local_array2_set
            case 214: // set_program_priority(priority)

                // TODO: This function

                return null; // set_program_priority
            case 215: // set_application_title(title)

                // TODO: This function

                return null; // set_application_title
            case 216: // keyboard_set_map(key1,key2)

                // TODO: This function

                return null; // keyboard_set_map
            case 217: // keyboard_get_map(key)

                // TODO: This function

                return null; // keyboard_get_map
            case 218: // keyboard_unset_map()

                // TODO: This function

                return null; // keyboard_unset_map
            case 219: // keyboard_check(key)
                return new Constant(KeyboardLibrary.keyboard_check((int) arg0.dVal));
            case 220: // keyboard_check_pressed(key)

                // TODO: This function

                return null; // keyboard_check_pressed
            case 221: // keyboard_check_released(key)

                // TODO: This function

                return null; // keyboard_check_released
            case 222: // keyboard_check_direct(key)

                // TODO: This function

                return null; // keyboard_check_direct
            case 223: // keyboard_get_numlock()

                // TODO: This function

                return null; // keyboard_get_numlock
            case 224: // keyboard_set_numlock(on)

                // TODO: This function

                return null; // keyboard_set_numlock
            case 225: // keyboard_key_press(key)

                // TODO: This function

                return null; // keyboard_key_press
            case 226: // keyboard_key_release(key)

                // TODO: This function

                return null; // keyboard_key_release
            case 227: // keyboard_clear(key)

                // TODO: This function

                return null; // keyboard_clear
            case 228: // io_clear()

                // TODO: This function

                return null; // io_clear
            case 229: // io_handle()

                // TODO: This function

                return null; // io_handle
            case 230: // keyboard_wait()

                // TODO: This function

                return null; // keyboard_wait
            case 231: // mouse_check_button(button)

                // TODO: This function

                return null; // mouse_check_button
            case 232: // mouse_check_button_pressed(button)

                // TODO: This function

                return null; // mouse_check_button_pressed
            case 233: // mouse_check_button_released(button)

                // TODO: This function

                return null; // mouse_check_button_released
            case 234: // mouse_wheel_up()

                // TODO: This function

                return null; // mouse_wheel_up
            case 235: // mouse_wheel_down()

                // TODO: This function

                return null; // mouse_wheel_down
            case 236: // mouse_clear(button)

                // TODO: This function

                return null; // mouse_clear
            case 237: // mouse_wait()

                // TODO: This function

                return null; // mouse_wait
            case 238: // joystick_exists(id)

                // TODO: This function

                return null; // joystick_exists
            case 239: // joystick_direction(id)

                // TODO: This function

                return null; // joystick_direction
            case 240: // joystick_name(id)

                // TODO: This function

                return null; // joystick_name
            case 241: // joystick_axes(id)

                // TODO: This function

                return null; // joystick_axes
            case 242: // joystick_buttons(id)

                // TODO: This function

                return null; // joystick_buttons
            case 243: // joystick_has_pov(id)

                // TODO: This function

                return null; // joystick_has_pov
            case 244: // joystick_check_button(id,button)

                // TODO: This function

                return null; // joystick_check_button
            case 245: // joystick_xpos(id)

                // TODO: This function

                return null; // joystick_xpos
            case 246: // joystick_ypos(id)

                // TODO: This function

                return null; // joystick_ypos
            case 247: // joystick_zpos(id)

                // TODO: This function

                return null; // joystick_zpos
            case 248: // joystick_rpos(id)

                // TODO: This function

                return null; // joystick_rpos
            case 249: // joystick_upos(id)

                // TODO: This function

                return null; // joystick_upos
            case 250: // joystick_vpos(id)

                // TODO: This function

                return null; // joystick_vpos
            case 251: // joystick_pov(id)

                // TODO: This function

                return null; // joystick_pov
            case 252: // draw_self()

                // TODO: This function

                return null; // draw_self
            case 253: // YoYo_GetPlatform()

                // TODO: This function

                return null; // YoYo_GetPlatform
            case 254: // YoYo_EnableAlphaBlend(enable)

                // TODO: This function

                return null; // YoYo_EnableAlphaBlend
            case 255: // draw_sprite(sprite,subimg,x,y)

                // TODO: This function

                return null; // draw_sprite
            case 256: // draw_sprite_pos(sprite,subimg,x1,y1,x2,y2,x3,y3,x4,y4,alpha)

                // TODO: This function

                return null; // draw_sprite_pos
            case 257: // draw_sprite_ext(sprite,subimg,x,y,xscale,yscale,rot,color,alpha)

                // TODO: This function

                return null; // draw_sprite_ext
            case 258: // draw_sprite_stretched(sprite,subimg,x,y,w,h)

                // TODO: This function

                return null; // draw_sprite_stretched
            case 259: // draw_sprite_stretched_ext(sprite,subimg,x,y,w,h,color,alpha)

                // TODO: This function

                return null; // draw_sprite_stretched_ext
            case 260: // draw_sprite_tiled(sprite,subimg,x,y)

                // TODO: This function

                return null; // draw_sprite_tiled
            case 261: // draw_sprite_tiled_ext(sprite,subimg,x,y,xscale,yscale,color,alpha)

                // TODO: This function

                return null; // draw_sprite_tiled_ext
            case 262: // draw_sprite_part(sprite,subimg,left,top,width,height,x,y)

                // TODO: This function

                return null; // draw_sprite_part
            case 263: // draw_sprite_part_ext(sprite,subimg,left,top,width,height,x,y,xscale,yscale,color,alpha)

                // TODO: This function

                return null; // draw_sprite_part_ext
            case 264: // draw_sprite_general(sprite,subimg,left,top,width,height,x,y,xscale,yscale,rot,c1,c2,c3,c4,alpha)

                // TODO: This function

                return null; // draw_sprite_general
            case 265: // draw_background(back,x,y)

                // TODO: This function

                return null; // draw_background
            case 266: // draw_background_ext(back,x,y,xscale,yscale,rot,color,alpha)

                // TODO: This function

                return null; // draw_background_ext
            case 267: // draw_background_stretched(back,x,y,w,h)

                // TODO: This function

                return null; // draw_background_stretched
            case 268: // draw_background_stretched_ext(back,x,y,w,h,color,alpha)

                // TODO: This function

                return null; // draw_background_stretched_ext
            case 269: // draw_background_tiled(back,x,y)

                // TODO: This function

                return null; // draw_background_tiled
            case 270: // draw_background_tiled_ext(back,x,y,xscale,yscale,color,alpha)

                // TODO: This function

                return null; // draw_background_tiled_ext
            case 271: // draw_background_part(back,left,top,width,height,x,y)

                // TODO: This function

                return null; // draw_background_part
            case 272: // draw_background_part_ext(back,left,top,width,height,x,y,xscale,yscale,color,alpha)

                // TODO: This function

                return null; // draw_background_part_ext
            case 273: // draw_background_general(back,left,top,width,height,x,y,xscale,yscale,rot,c1,c2,c3,c4,alpha)

                // TODO: This function

                return null; // draw_background_general
            case 274: // draw_clear(col)

                // TODO: This function

                return null; // draw_clear
            case 275: // draw_clear_alpha(col,alpha)

                // TODO: This function

                return null; // draw_clear_alpha
            case 276: // draw_point(x,y)

                // TODO: This function

                return null; // draw_point
            case 277: // draw_line(x1,y1,x2,y2)

                // TODO: This function

                return null; // draw_line
            case 278: // draw_line_width(x1,y1,x2,y2,w)

                // TODO: This function

                return null; // draw_line_width
            case 279: // draw_rectangle(x1,y1,x2,y2,outline)

                // TODO: This function

                return null; // draw_rectangle
            case 280: // draw_roundrect(x1,y1,x2,y2,outline)

                // TODO: This function

                return null; // draw_roundrect
            case 281: // draw_triangle(x1,y1,x2,y2,x3,y3,outline)

                // TODO: This function

                return null; // draw_triangle
            case 282: // draw_circle(x,y,r,outline)

                // TODO: This function

                return null; // draw_circle
            case 283: // draw_ellipse(x1,y1,x2,y2,outline)

                // TODO: This function

                return null; // draw_ellipse
            case 284: // draw_set_circle_precision(precision)

                // TODO: This function

                return null; // draw_set_circle_precision
            case 285: // draw_arrow(x1,y1,x2,y2,size)

                // TODO: This function

                return null; // draw_arrow
            case 286: // draw_button(x1,y1,x2,y2,up)

                // TODO: This function

                return null; // draw_button
            case 287: // draw_path(path,x,y,absolute)

                // TODO: This function

                return null; // draw_path
            case 288: // draw_healthbar(x1,y1,x2,y2,amount,backcol,mincol,maxcol,direction,showback,showborder)

                // TODO: This function

                return null; // draw_healthbar
            case 289: // draw_getpixel(x,y)

                // TODO: This function

                return null; // draw_getpixel
            case 290: // draw_set_color(col)

                // TODO: This function

                return null; // draw_set_color
            case 291: // draw_set_alpha(alpha)

                // TODO: This function

                return null; // draw_set_alpha
            case 292: // draw_get_color()

                // TODO: This function

                return null; // draw_get_color
            case 293: // draw_get_alpha()

                // TODO: This function

                return null; // draw_get_alpha
            case 294: // make_color_rgb(red,green,blue)

                // TODO: This function

                return null; // make_color_rgb
            case 295: // make_color_hsv(hue,saturation,value)

                // TODO: This function

                return null; // make_color_hsv
            case 296: // color_get_red(col)

                // TODO: This function

                return null; // color_get_red
            case 297: // color_get_green(col)

                // TODO: This function

                return null; // color_get_green
            case 298: // color_get_blue(col)

                // TODO: This function

                return null; // color_get_blue
            case 299: // color_get_hue(col)

                // TODO: This function

                return null; // color_get_hue
            case 300: // color_get_saturation(col)

                // TODO: This function

                return null; // color_get_saturation
            case 301: // color_get_value(col)

                // TODO: This function

                return null; // color_get_value
            case 302: // merge_color(col1,col2,amount)

                // TODO: This function

                return null; // merge_color
            case 303: // screen_save(fname)

                // TODO: This function

                return null; // screen_save
            case 304: // screen_save_part(fname,x,y,w,h)

                // TODO: This function

                return null; // screen_save_part
            case 305: // draw_set_font(font)

                // TODO: This function

                return null; // draw_set_font
            case 306: // draw_set_halign(halign)

                // TODO: This function

                return null; // draw_set_halign
            case 307: // draw_set_valign(valign)

                // TODO: This function

                return null; // draw_set_valign
            case 308: // draw_text(x,y,string)

                // TODO: This function

                return null; // draw_text
            case 309: // draw_text_ext(x,y,string,sep,w)

                // TODO: This function

                return null; // draw_text_ext
            case 310: // string_width(string)

                // TODO: This function

                return null; // string_width
            case 311: // string_height(string)

                // TODO: This function

                return null; // string_height
            case 312: // string_width_ext(string,sep,w)

                // TODO: This function

                return null; // string_width_ext
            case 313: // string_height_ext(string,sep,w)

                // TODO: This function

                return null; // string_height_ext
            case 314: // draw_text_transformed(x,y,string,xscale,yscale,angle)

                // TODO: This function

                return null; // draw_text_transformed
            case 315: // draw_text_ext_transformed(x,y,string,sep,w,xscale,yscale,angle)

                // TODO: This function

                return null; // draw_text_ext_transformed
            case 316: // draw_text_color(x,y,string,c1,c2,c3,c4,alpha)

                // TODO: This function

                return null; // draw_text_color
            case 317: // draw_text_ext_color(x,y,string,sep,w,c1,c2,c3,c4,alpha)

                // TODO: This function

                return null; // draw_text_ext_color
            case 318: // draw_text_transformed_color(x,y,string,xscale,yscale,angle,c1,c2,c3,c4,alpha)

                // TODO: This function

                return null; // draw_text_transformed_color
            case 319: // draw_text_ext_transformed_color(x,y,string,sep,w,xscale,yscale,angle,c1,c2,c3,c4,alpha)

                // TODO: This function

                return null; // draw_text_ext_transformed_color
            case 320: // draw_point_color(x,y,col1)

                // TODO: This function

                return null; // draw_point_color
            case 321: // draw_line_color(x1,y1,x2,y2,col1,col2)

                // TODO: This function

                return null; // draw_line_color
            case 322: // draw_line_width_color(x1,y1,x2,y2,w,col1,col2)

                // TODO: This function

                return null; // draw_line_width_color
            case 323: // draw_rectangle_color(x1,y1,x2,y2,col1,col2,col3,col4,outline)

                // TODO: This function

                return null; // draw_rectangle_color
            case 324: // draw_roundrect_color(x1,y1,x2,y2,col1,col2,outline)

                // TODO: This function

                return null; // draw_roundrect_color
            case 325: // draw_triangle_color(x1,y1,x2,y2,x3,y3,col1,col2,col3,outline)

                // TODO: This function

                return null; // draw_triangle_color
            case 326: // draw_circle_color(x,y,r,col1,col2,outline)

                // TODO: This function

                return null; // draw_circle_color
            case 327: // draw_ellipse_color(x1,y1,x2,y2,col1,col2,outline)

                // TODO: This function

                return null; // draw_ellipse_color
            case 328: // draw_primitive_begin(kind)

                // TODO: This function

                return null; // draw_primitive_begin
            case 329: // draw_vertex(x,y)

                // TODO: This function

                return null; // draw_vertex
            case 330: // draw_vertex_color(x,y,col,alpha)

                // TODO: This function

                return null; // draw_vertex_color
            case 331: // draw_primitive_end()

                // TODO: This function

                return null; // draw_primitive_end
            case 332: // sprite_get_texture(spr,subimg)

                // TODO: This function

                return null; // sprite_get_texture
            case 333: // background_get_texture(back)

                // TODO: This function

                return null; // background_get_texture
            case 334: // texture_preload(texid)

                // TODO: This function

                return null; // texture_preload
            case 335: // texture_set_priority(texid,prio)

                // TODO: This function

                return null; // texture_set_priority
            case 336: // texture_get_width(texid)

                // TODO: This function

                return null; // texture_get_width
            case 337: // texture_get_height(texid)

                // TODO: This function

                return null; // texture_get_height
            case 338: // draw_primitive_begin_texture(kind,texid)

                // TODO: This function

                return null; // draw_primitive_begin_texture
            case 339: // draw_vertex_texture(x,y,xtex,ytex)

                // TODO: This function

                return null; // draw_vertex_texture
            case 340: // draw_vertex_texture_color(x,y,xtex,ytex,col,alpha)

                // TODO: This function

                return null; // draw_vertex_texture_color
            case 341: // texture_set_interpolation(linear)

                // TODO: This function

                return null; // texture_set_interpolation
            case 342: // texture_set_blending(blend)

                // TODO: This function

                return null; // texture_set_blending
            case 343: // texture_set_repeat(repeat)

                // TODO: This function

                return null; // texture_set_repeat
            case 344: // draw_set_blend_mode(mode)

                // TODO: This function

                return null; // draw_set_blend_mode
            case 345: // draw_set_blend_mode_ext(src,dest)

                // TODO: This function

                return null; // draw_set_blend_mode_ext
            case 346: // surface_create(w,h)

                // TODO: This function

                return null; // surface_create
            case 347: // surface_free(id)

                // TODO: This function

                return null; // surface_free
            case 348: // surface_exists(id)

                // TODO: This function

                return null; // surface_exists
            case 349: // surface_get_width(id)

                // TODO: This function

                return null; // surface_get_width
            case 350: // surface_get_height(id)

                // TODO: This function

                return null; // surface_get_height
            case 351: // surface_get_texture(id)

                // TODO: This function

                return null; // surface_get_texture
            case 352: // surface_set_target(id)

                // TODO: This function

                return null; // surface_set_target
            case 353: // surface_reset_target()

                // TODO: This function

                return null; // surface_reset_target
            case 354: // draw_surface(id,x,y)

                // TODO: This function

                return null; // draw_surface
            case 355: // draw_surface_stretched(id,x,y,w,h)

                // TODO: This function

                return null; // draw_surface_stretched
            case 356: // draw_surface_tiled(id,x,y)

                // TODO: This function

                return null; // draw_surface_tiled
            case 357: // draw_surface_part(id,left,top,width,height,x,y)

                // TODO: This function

                return null; // draw_surface_part
            case 358: // draw_surface_ext(id,x,y,xscale,yscale,rot,color,alpha)

                // TODO: This function

                return null; // draw_surface_ext
            case 359: // draw_surface_stretched_ext(id,x,y,w,h,color,alpha)

                // TODO: This function

                return null; // draw_surface_stretched_ext
            case 360: // draw_surface_tiled_ext(id,x,y,xscale,yscale,color,alpha)

                // TODO: This function

                return null; // draw_surface_tiled_ext
            case 361: // draw_surface_part_ext(id,left,top,width,height,x,y,xscale,yscale,color,alpha)

                // TODO: This function

                return null; // draw_surface_part_ext
            case 362: // draw_surface_general(id,left,top,width,height,x,y,xscale,yscale,rot,c1,c2,c3,c4,alpha)

                // TODO: This function

                return null; // draw_surface_general
            case 363: // surface_getpixel(id,x,y)

                // TODO: This function

                return null; // surface_getpixel
            case 364: // surface_save(id,fname)

                // TODO: This function

                return null; // surface_save
            case 365: // surface_save_part(id,fname,x,y,w,h)

                // TODO: This function

                return null; // surface_save_part
            case 366: // surface_copy(destination,x,y,source)

                // TODO: This function

                return null; // surface_copy
            case 367: // surface_copy_part(destination,x,y,source,xs,ys,ws,hs)

                // TODO: This function

                return null; // surface_copy_part
            case 368: // tile_add(background,left,top,width,height,x,y,depth)

                // TODO: This function

                return null; // tile_add
            case 369: // tile_delete(id)

                // TODO: This function

                return null; // tile_delete
            case 370: // tile_exists(id)

                // TODO: This function

                return null; // tile_exists
            case 371: // tile_get_x(id)

                // TODO: This function

                return null; // tile_get_x
            case 372: // tile_get_y(id)

                // TODO: This function

                return null; // tile_get_y
            case 373: // tile_get_left(id)

                // TODO: This function

                return null; // tile_get_left
            case 374: // tile_get_top(id)

                // TODO: This function

                return null; // tile_get_top
            case 375: // tile_get_width(id)

                // TODO: This function

                return null; // tile_get_width
            case 376: // tile_get_height(id)

                // TODO: This function

                return null; // tile_get_height
            case 377: // tile_get_depth(id)

                // TODO: This function

                return null; // tile_get_depth
            case 378: // tile_get_visible(id)

                // TODO: This function

                return null; // tile_get_visible
            case 379: // tile_get_xscale(id)

                // TODO: This function

                return null; // tile_get_xscale
            case 380: // tile_get_yscale(id)

                // TODO: This function

                return null; // tile_get_yscale
            case 381: // tile_get_background(id)

                // TODO: This function

                return null; // tile_get_background
            case 382: // tile_get_blend(id)

                // TODO: This function

                return null; // tile_get_blend
            case 383: // tile_get_alpha(id)

                // TODO: This function

                return null; // tile_get_alpha
            case 384: // tile_set_position(id,x,y)

                // TODO: This function

                return null; // tile_set_position
            case 385: // tile_set_region(id,left,top,width,height)

                // TODO: This function

                return null; // tile_set_region
            case 386: // tile_set_background(id,background)

                // TODO: This function

                return null; // tile_set_background
            case 387: // tile_set_visible(id,visible)

                // TODO: This function

                return null; // tile_set_visible
            case 388: // tile_set_depth(id,depth)

                // TODO: This function

                return null; // tile_set_depth
            case 389: // tile_set_scale(id,xscale,yscale)

                // TODO: This function

                return null; // tile_set_scale
            case 390: // tile_set_blend(id,color)

                // TODO: This function

                return null; // tile_set_blend
            case 391: // tile_set_alpha(id,alpha)

                // TODO: This function

                return null; // tile_set_alpha
            case 392: // tile_layer_hide(depth)

                // TODO: This function

                return null; // tile_layer_hide
            case 393: // tile_layer_show(depth)

                // TODO: This function

                return null; // tile_layer_show
            case 394: // tile_layer_delete(depth)

                // TODO: This function

                return null; // tile_layer_delete
            case 395: // tile_layer_shift(depth,x,y)

                // TODO: This function

                return null; // tile_layer_shift
            case 396: // tile_layer_find(depth,x,y)

                // TODO: This function

                return null; // tile_layer_find
            case 397: // tile_layer_delete_at(depth,x,y)

                // TODO: This function

                return null; // tile_layer_delete_at
            case 398: // tile_layer_depth(depth,newdepth)

                // TODO: This function

                return null; // tile_layer_depth
            case 399: // display_get_width()

                // TODO: This function

                return null; // display_get_width
            case 400: // display_get_height()

                // TODO: This function

                return null; // display_get_height
            case 401: // display_get_colordepth()

                // TODO: This function

                return null; // display_get_colordepth
            case 402: // display_get_frequency()

                // TODO: This function

                return null; // display_get_frequency
            case 403: // display_set_size(w,h)

                // TODO: This function

                return null; // display_set_size
            case 404: // display_set_colordepth(coldepth)

                // TODO: This function

                return null; // display_set_colordepth
            case 405: // display_set_frequency(frequency)

                // TODO: This function

                return null; // display_set_frequency
            case 406: // display_set_all(w,h,frequency,coldepth)

                // TODO: This function

                return null; // display_set_all
            case 407: // display_test_all(w,h,frequency,coldepth)

                // TODO: This function

                return null; // display_test_all
            case 408: // display_reset(aa_level)

                // TODO: This function

                return null; // display_reset
            case 409: // display_mouse_get_x()

                // TODO: This function

                return null; // display_mouse_get_x
            case 410: // display_mouse_get_y()

                // TODO: This function

                return null; // display_mouse_get_y
            case 411: // display_mouse_set(x,y)

                // TODO: This function

                return null; // display_mouse_set
            case 412: // window_set_visible(visible)

                // TODO: This function

                return null; // window_set_visible
            case 413: // window_get_visible()

                // TODO: This function

                return null; // window_get_visible
            case 414: // window_set_fullscreen(full)

                // TODO: This function

                return null; // window_set_fullscreen
            case 415: // window_get_fullscreen()

                // TODO: This function

                return null; // window_get_fullscreen
            case 416: // window_set_showborder(show)

                // TODO: This function

                return null; // window_set_showborder
            case 417: // window_get_showborder()

                // TODO: This function

                return null; // window_get_showborder
            case 418: // window_set_showicons(show)

                // TODO: This function

                return null; // window_set_showicons
            case 419: // window_get_showicons()

                // TODO: This function

                return null; // window_get_showicons
            case 420: // window_set_stayontop(stay)

                // TODO: This function

                return null; // window_set_stayontop
            case 421: // window_get_stayontop()

                // TODO: This function

                return null; // window_get_stayontop
            case 422: // window_set_sizeable(sizeable)

                // TODO: This function

                return null; // window_set_sizeable
            case 423: // window_get_sizeable()

                // TODO: This function

                return null; // window_get_sizeable
            case 424: // window_set_caption(caption)

                // TODO: This function

                return null; // window_set_caption
            case 425: // window_get_caption()

                // TODO: This function

                return null; // window_get_caption
            case 426: // window_set_cursor(curs)

                // TODO: This function

                return null; // window_set_cursor
            case 427: // window_get_cursor()

                // TODO: This function

                return null; // window_get_cursor
            case 428: // window_set_color(color)

                // TODO: This function

                return null; // window_set_color
            case 429: // window_get_color()

                // TODO: This function

                return null; // window_get_color
            case 430: // window_set_region_scale(scale,adaptwindow)

                // TODO: This function

                return null; // window_set_region_scale
            case 431: // window_get_region_scale()

                // TODO: This function

                return null; // window_get_region_scale
            case 432: // window_set_position(x,y)

                // TODO: This function

                return null; // window_set_position
            case 433: // window_set_size(w,h)

                // TODO: This function

                return null; // window_set_size
            case 434: // window_set_rectangle(x,y,w,h)

                // TODO: This function

                return null; // window_set_rectangle
            case 435: // window_center()

                // TODO: This function

                return null; // window_center
            case 436: // window_default()

                // TODO: This function

                return null; // window_default
            case 437: // window_get_x()

                // TODO: This function

                return null; // window_get_x
            case 438: // window_get_y()

                // TODO: This function

                return null; // window_get_y
            case 439: // window_get_width()

                // TODO: This function

                return null; // window_get_width
            case 440: // window_get_height()

                // TODO: This function

                return null; // window_get_height
            case 441: // window_mouse_get_x()

                // TODO: This function

                return null; // window_mouse_get_x
            case 442: // window_mouse_get_y()

                // TODO: This function

                return null; // window_mouse_get_y
            case 443: // window_mouse_set(x,y)

                // TODO: This function

                return null; // window_mouse_set
            case 444: // window_set_region_size(w,h,adaptwindow)

                // TODO: This function

                return null; // window_set_region_size
            case 445: // window_get_region_width()

                // TODO: This function

                return null; // window_get_region_width
            case 446: // window_get_region_height()

                // TODO: This function

                return null; // window_get_region_height
            case 447: // window_view_mouse_get_x(id)

                // TODO: This function

                return null; // window_view_mouse_get_x
            case 448: // window_view_mouse_get_y(id)

                // TODO: This function

                return null; // window_view_mouse_get_y
            case 449: // window_view_mouse_set(id,x,y)

                // TODO: This function

                return null; // window_view_mouse_set
            case 450: // window_views_mouse_get_x()

                // TODO: This function

                return null; // window_views_mouse_get_x
            case 451: // window_views_mouse_get_y()

                // TODO: This function

                return null; // window_views_mouse_get_y
            case 452: // window_views_mouse_set(x,y)

                // TODO: This function

                return null; // window_views_mouse_set
            case 453: // screen_redraw()

                // TODO: This function

                return null; // screen_redraw
            case 454: // screen_refresh()

                // TODO: This function

                return null; // screen_refresh
            case 455: // screen_wait_vsync()

                // TODO: This function

                return null; // screen_wait_vsync
            case 456: // set_automatic_draw(value)

                // TODO: This function

                return null; // set_automatic_draw
            case 457: // set_synchronization(value)

                // TODO: This function

                return null; // set_synchronization
            case 458: // sound_play(index)

                // TODO: This function

                return null; // sound_play
            case 459: // sound_loop(index)

                // TODO: This function

                return null; // sound_loop
            case 460: // sound_stop(index)

                // TODO: This function

                return null; // sound_stop
            case 461: // sound_stop_all()

                // TODO: This function

                return null; // sound_stop_all
            case 462: // sound_isplaying(index)

                // TODO: This function

                return null; // sound_isplaying
            case 463: // sound_volume(index,value)

                // TODO: This function

                return null; // sound_volume
            case 464: // sound_global_volume(value)

                // TODO: This function

                return null; // sound_global_volume
            case 465: // sound_fade(index,value,time)

                // TODO: This function

                return null; // sound_fade
            case 466: // sound_pan(index,value)

                // TODO: This function

                return null; // sound_pan
            case 467: // sound_background_tempo(factor)

                // TODO: This function

                return null; // sound_background_tempo
            case 468: // sound_set_search_directory(dir)

                // TODO: This function

                return null; // sound_set_search_directory
            case 469: // sound_effect_set(snd,effect)

                // TODO: This function

                return null; // sound_effect_set
            case 470: // sound_effect_chorus(snd,wetdry,depth,feedback,frequency,wave,delay,phase)

                // TODO: This function

                return null; // sound_effect_chorus
            case 471: // sound_effect_echo(snd,wetdry,feedback,leftdelay,rightdelay,pandelay)

                // TODO: This function

                return null; // sound_effect_echo
            case 472: // sound_effect_flanger(snd,wetdry,depth,feedback,frequency,wave,delay,phase)

                // TODO: This function

                return null; // sound_effect_flanger
            case 473: // sound_effect_gargle(snd,rate,wave)

                // TODO: This function

                return null; // sound_effect_gargle
            case 474: // sound_effect_reverb(snd,gain,mix,time,ratio)

                // TODO: This function

                return null; // sound_effect_reverb
            case 475: // sound_effect_compressor(snd,gain,attack,release,threshold,ratio,delay)

                // TODO: This function

                return null; // sound_effect_compressor
            case 476: // sound_effect_equalizer(snd,center,bandwidth,gain)

                // TODO: This function

                return null; // sound_effect_equalizer
            case 477: // sound_3d_set_sound_position(snd,x,y,z)

                // TODO: This function

                return null; // sound_3d_set_sound_position
            case 478: // sound_3d_set_sound_velocity(snd,x,y,z)

                // TODO: This function

                return null; // sound_3d_set_sound_velocity
            case 479: // sound_3d_set_sound_distance(snd,mindist,maxdist)

                // TODO: This function

                return null; // sound_3d_set_sound_distance
            case 480: // sound_3d_set_sound_cone(snd,x,y,z,anglein,angleout,voloutside)

                // TODO: This function

                return null; // sound_3d_set_sound_cone
            case 481: // cd_init()

                // TODO: This function

                return null; // cd_init
            case 482: // cd_present()

                // TODO: This function

                return null; // cd_present
            case 483: // cd_number()

                // TODO: This function

                return null; // cd_number
            case 484: // cd_playing()

                // TODO: This function

                return null; // cd_playing
            case 485: // cd_paused()

                // TODO: This function

                return null; // cd_paused
            case 486: // cd_track()

                // TODO: This function

                return null; // cd_track
            case 487: // cd_length()

                // TODO: This function

                return null; // cd_length
            case 488: // cd_track_length(n)

                // TODO: This function

                return null; // cd_track_length
            case 489: // cd_position()

                // TODO: This function

                return null; // cd_position
            case 490: // cd_track_position()

                // TODO: This function

                return null; // cd_track_position
            case 491: // cd_play(first,last)

                // TODO: This function

                return null; // cd_play
            case 492: // cd_stop()

                // TODO: This function

                return null; // cd_stop
            case 493: // cd_pause()

                // TODO: This function

                return null; // cd_pause
            case 494: // cd_resume()

                // TODO: This function

                return null; // cd_resume
            case 495: // cd_set_position(pos)

                // TODO: This function

                return null; // cd_set_position
            case 496: // cd_set_track_position(pos)

                // TODO: This function

                return null; // cd_set_track_position
            case 497: // cd_open_door()

                // TODO: This function

                return null; // cd_open_door
            case 498: // cd_close_door()

                // TODO: This function

                return null; // cd_close_door
            case 499: // MCI_command(str)

                // TODO: This function

                return null; // MCI_command
            case 500: // splash_show_video(fname,loop)

                // TODO: This function

                return null; // splash_show_video
            case 501: // splash_show_text(fname,delay)

                // TODO: This function

                return null; // splash_show_text
            case 502: // splash_show_web(url,delay)

                // TODO: This function

                return null; // splash_show_web
            case 503: // splash_show_image(fname,delay)

                // TODO: This function

                return null; // splash_show_image
            case 504: // splash_set_caption(cap)

                // TODO: This function

                return null; // splash_set_caption
            case 505: // splash_set_fullscreen(full)

                // TODO: This function

                return null; // splash_set_fullscreen
            case 506: // splash_set_border(border)

                // TODO: This function

                return null; // splash_set_border
            case 507: // splash_set_size(w,h)

                // TODO: This function

                return null; // splash_set_size
            case 508: // splash_set_position(x,y)

                // TODO: This function

                return null; // splash_set_position
            case 509: // splash_set_adapt(adapt)

                // TODO: This function

                return null; // splash_set_adapt
            case 510: // splash_set_top(top)

                // TODO: This function

                return null; // splash_set_top
            case 511: // splash_set_color(col)

                // TODO: This function

                return null; // splash_set_color
            case 512: // splash_set_main(main)

                // TODO: This function

                return null; // splash_set_main
            case 513: // splash_set_scale(scale)

                // TODO: This function

                return null; // splash_set_scale
            case 514: // splash_set_cursor(vis)

                // TODO: This function

                return null; // splash_set_cursor
            case 515: // splash_set_interrupt(interrupt)

                // TODO: This function

                return null; // splash_set_interrupt
            case 516: // splash_set_stop_key(stop)

                // TODO: This function

                return null; // splash_set_stop_key
            case 517: // splash_set_stop_mouse(stop)

                // TODO: This function

                return null; // splash_set_stop_mouse
            case 518: // splash_set_close_button(show)

                // TODO: This function

                return null; // splash_set_close_button
            case 519: // show_info()

                // TODO: This function

                return null; // show_info
            case 520: // load_info(fname)

                // TODO: This function

                return null; // load_info
            case 521: // show_message(str)

                // TODO: This function

                return null; // show_message
            case 522: // show_message_ext(str,but1,but2,but3)

                // TODO: This function

                return null; // show_message_ext
            case 523: // show_question(str)

                // TODO: This function

                return null; // show_question
            case 524: // get_integer(str,def)

                // TODO: This function

                return null; // get_integer
            case 525: // get_string(str,def)

                // TODO: This function

                return null; // get_string
            case 526: // message_background(back)

                // TODO: This function

                return null; // message_background
            case 527: // message_button(sprite)

                // TODO: This function

                return null; // message_button
            case 528: // message_alpha(alpha)

                // TODO: This function

                return null; // message_alpha
            case 529: // message_text_font(name,size,color,style)

                // TODO: This function

                return null; // message_text_font
            case 530: // message_button_font(name,size,color,style)

                // TODO: This function

                return null; // message_button_font
            case 531: // message_input_font(name,size,color,style)

                // TODO: This function

                return null; // message_input_font
            case 532: // message_text_charset(type, charset_id)

                // TODO: This function

                return null; // message_text_charset
            case 533: // message_mouse_color(col)

                // TODO: This function

                return null; // message_mouse_color
            case 534: // message_input_color(col)

                // TODO: This function

                return null; // message_input_color
            case 535: // message_position(x,y)

                // TODO: This function

                return null; // message_position
            case 536: // message_size(w,h)

                // TODO: This function

                return null; // message_size
            case 537: // message_caption(show,str)

                // TODO: This function

                return null; // message_caption
            case 538: // show_menu(str,def)

                // TODO: This function

                return null; // show_menu
            case 539: // show_menu_pos(x,y,str,def)

                // TODO: This function

                return null; // show_menu_pos
            case 540: // get_color(defcol)

                // TODO: This function

                return null; // get_color
            case 541: // get_open_filename(filter,fname)

                // TODO: This function

                return null; // get_open_filename
            case 542: // get_save_filename(filter,fname)

                // TODO: This function

                return null; // get_save_filename
            case 543: // get_directory(dname)

                // TODO: This function

                return null; // get_directory
            case 544: // get_directory_alt(capt,root)

                // TODO: This function

                return null; // get_directory_alt
            case 545: // show_error(str,abort)

                // TODO: This function

                return null; // show_error
            case 546: // highscore_show_ext(numb,back,border,col1,col2,name,size)

                // TODO: This function

                return null; // highscore_show_ext
            case 547: // highscore_show(numb)

                // TODO: This function

                return null; // highscore_show
            case 548: // highscore_set_background(back);

                // TODO: This function

                return null; // highscore_set_background
            case 549: // highscore_set_border(show);

                // TODO: This function

                return null; // highscore_set_border
            case 550: // highscore_set_font(name,size,style);

                // TODO: This function

                return null; // highscore_set_font
            case 551: // highscore_set_strings(caption,nobody,escape);

                // TODO: This function

                return null; // highscore_set_strings
            case 552: // highscore_set_colors(back,new,other);

                // TODO: This function

                return null; // highscore_set_colors
            case 553: // highscore_clear()

                // TODO: This function

                return null; // highscore_clear
            case 554: // highscore_add(str,numb)

                // TODO: This function

                return null; // highscore_add
            case 555: // highscore_add_current()

                // TODO: This function

                return null; // highscore_add_current
            case 556: // highscore_value(place)

                // TODO: This function

                return null; // highscore_value
            case 557: // highscore_name(place)

                // TODO: This function

                return null; // highscore_name
            case 558: // draw_highscore(x1,y1,x2,y2)

                // TODO: This function

                return null; // draw_highscore
            case 559: // sprite_exists(ind)

                // TODO: This function

                return null; // sprite_exists
            case 560: // sprite_get_name(ind)

                // TODO: This function

                return null; // sprite_get_name
            case 561: // sprite_get_number(ind)

                // TODO: This function

                return null; // sprite_get_number
            case 562: // sprite_get_width(ind)

                // TODO: This function

                return null; // sprite_get_width
            case 563: // sprite_get_height(ind)

                // TODO: This function

                return null; // sprite_get_height
            case 564: // sprite_get_xoffset(ind)

                // TODO: This function

                return null; // sprite_get_xoffset
            case 565: // sprite_get_yoffset(ind)

                // TODO: This function

                return null; // sprite_get_yoffset
            case 566: // sprite_get_bbox_left(ind)

                // TODO: This function

                return null; // sprite_get_bbox_left
            case 567: // sprite_get_bbox_right(ind)

                // TODO: This function

                return null; // sprite_get_bbox_right
            case 568: // sprite_get_bbox_top(ind)

                // TODO: This function

                return null; // sprite_get_bbox_top
            case 569: // sprite_get_bbox_bottom(ind)

                // TODO: This function

                return null; // sprite_get_bbox_bottom
            case 570: // sprite_save(ind,subimg,fname)

                // TODO: This function

                return null; // sprite_save
            case 571: // sprite_save_strip(ind,fname)

                // TODO: This function

                return null; // sprite_save_strip
            case 572: // sound_exists(ind)

                // TODO: This function

                return null; // sound_exists
            case 573: // sound_get_name(ind)

                // TODO: This function

                return null; // sound_get_name
            case 574: // sound_get_kind(ind)

                // TODO: This function

                return null; // sound_get_kind
            case 575: // sound_get_preload(ind)

                // TODO: This function

                return null; // sound_get_preload
            case 576: // sound_discard(ind)

                // TODO: This function

                return null; // sound_discard
            case 577: // sound_restore(ind)

                // TODO: This function

                return null; // sound_restore
            case 578: // background_exists(ind)

                // TODO: This function

                return null; // background_exists
            case 579: // background_get_name(ind)

                // TODO: This function

                return null; // background_get_name
            case 580: // background_get_width(ind)

                // TODO: This function

                return null; // background_get_width
            case 581: // background_get_height(ind)

                // TODO: This function

                return null; // background_get_height
            case 582: // background_save(ind,fname)

                // TODO: This function

                return null; // background_save
            case 583: // font_exists(ind)

                // TODO: This function

                return null; // font_exists
            case 584: // font_get_name(ind)

                // TODO: This function

                return null; // font_get_name
            case 585: // font_get_fontname(ind)

                // TODO: This function

                return null; // font_get_fontname
            case 586: // font_get_bold(ind)

                // TODO: This function

                return null; // font_get_bold
            case 587: // font_get_italic(ind)

                // TODO: This function

                return null; // font_get_italic
            case 588: // font_get_first(ind)

                // TODO: This function

                return null; // font_get_first
            case 589: // font_get_last(ind)

                // TODO: This function

                return null; // font_get_last
            case 590: // path_exists(ind)

                // TODO: This function

                return null; // path_exists
            case 591: // path_get_name(ind)

                // TODO: This function

                return null; // path_get_name
            case 592: // path_get_length(ind)

                // TODO: This function

                return null; // path_get_length
            case 593: // path_get_kind(ind)

                // TODO: This function

                return null; // path_get_kind
            case 594: // path_get_closed(ind)

                // TODO: This function

                return null; // path_get_closed
            case 595: // path_get_precision(ind)

                // TODO: This function

                return null; // path_get_precision
            case 596: // path_get_number(ind)

                // TODO: This function

                return null; // path_get_number
            case 597: // path_get_point_x(ind,n)

                // TODO: This function

                return null; // path_get_point_x
            case 598: // path_get_point_y(ind,n)

                // TODO: This function

                return null; // path_get_point_y
            case 599: // path_get_point_speed(ind,n)

                // TODO: This function

                return null; // path_get_point_speed
            case 600: // path_get_x(ind,pos)

                // TODO: This function

                return null; // path_get_x
            case 601: // path_get_y(ind,pos)

                // TODO: This function

                return null; // path_get_y
            case 602: // path_get_speed(ind,pos)

                // TODO: This function

                return null; // path_get_speed
            case 603: // script_exists(ind)

                // TODO: This function

                return null; // script_exists
            case 604: // script_get_name(ind)

                // TODO: This function

                return null; // script_get_name
            case 605: // script_get_text(ind)

                // TODO: This function

                return null; // script_get_text
            case 606: // timeline_exists(ind)

                // TODO: This function

                return null; // timeline_exists
            case 607: // timeline_get_name(ind)

                // TODO: This function

                return null; // timeline_get_name
            case 608: // object_exists(ind)

                // TODO: This function

                return null; // object_exists
            case 609: // object_get_name(ind)

                // TODO: This function

                return null; // object_get_name
            case 610: // object_get_sprite(ind)

                // TODO: This function

                return null; // object_get_sprite
            case 611: // object_get_solid(ind)

                // TODO: This function

                return null; // object_get_solid
            case 612: // object_get_visible(ind)

                // TODO: This function

                return null; // object_get_visible
            case 613: // object_get_depth(ind)

                // TODO: This function

                return null; // object_get_depth
            case 614: // object_get_persistent(ind)

                // TODO: This function

                return null; // object_get_persistent
            case 615: // object_get_mask(ind)

                // TODO: This function

                return null; // object_get_mask
            case 616: // object_get_parent(ind)

                // TODO: This function

                return null; // object_get_parent
            case 617: // object_is_ancestor(ind1,ind2)

                // TODO: This function

                return null; // object_is_ancestor
            case 618: // room_exists(ind)

                // TODO: This function

                return null; // room_exists
            case 619: // room_get_name(ind)

                // TODO: This function

                return null; // room_get_name
            case 620: // sprite_set_offset(ind,xoff,yoff)

                // TODO: This function

                return null; // sprite_set_offset
            case 621: // sprite_duplicate(ind)

                // TODO: This function

                return null; // sprite_duplicate
            case 622: // sprite_assign(ind,source)

                // TODO: This function

                return null; // sprite_assign
            case 623: // sprite_merge(ind1,ind2)

                // TODO: This function

                return null; // sprite_merge
            case 624: // sprite_add(fname,imgnumb,removeback,smooth,xorig,yorig)

                // TODO: This function

                return null; // sprite_add
            case 625: // sprite_replace(ind,fname,imgnumb,removeback,smooth,xorig,yorig)

                // TODO: This function

                return null; // sprite_replace
            case 626: // sprite_add_sprite(fname)

                // TODO: This function

                return null; // sprite_add_sprite
            case 627: // sprite_replace_sprite(ind,fname)

                // TODO: This function

                return null; // sprite_replace_sprite
            case 628: // sprite_create_from_screen(x,y,w,h,removeback,smooth,xorig,yorig)

                // TODO: This function

                return null; // sprite_create_from_screen
            case 629: // sprite_add_from_screen(ind,x,y,w,h,removeback,smooth)

                // TODO: This function

                return null; // sprite_add_from_screen
            case 630: // sprite_create_from_surface(id,x,y,w,h,removeback,smooth,xorig,yorig)

                // TODO: This function

                return null; // sprite_create_from_surface
            case 631: // sprite_add_from_surface(ind,id,x,y,w,h,removeback,smooth)

                // TODO: This function

                return null; // sprite_add_from_surface
            case 632: // sprite_delete(ind)

                // TODO: This function

                return null; // sprite_delete
            case 633: // sprite_set_alpha_from_sprite(ind,spr)

                // TODO: This function

                return null; // sprite_set_alpha_from_sprite
            case 634: // sprite_collision_mask(ind,sepmasks,bboxmode,bbleft,bbright,bbtop,bbbottom,kind,tolerance)

                // TODO: This function

                return null; // sprite_collision_mask
            case 635: // sound_add(fname,kind,preload)

                // TODO: This function

                return null; // sound_add
            case 636: // sound_replace(ind,fname,kind,preload)

                // TODO: This function

                return null; // sound_replace
            case 637: // sound_delete(ind)

                // TODO: This function

                return null; // sound_delete
            case 638: // background_duplicate(ind)

                // TODO: This function

                return null; // background_duplicate
            case 639: // background_assign(ind,source)

                // TODO: This function

                return null; // background_assign
            case 640: // background_add(fname,removeback,smooth)

                // TODO: This function

                return null; // background_add
            case 641: // background_replace(ind,fname,removeback,smooth)

                // TODO: This function

                return null; // background_replace
            case 642: // background_add_background(fname)

                // TODO: This function

                return null; // background_add_background
            case 643: // background_replace_background(ind,fname)

                // TODO: This function

                return null; // background_replace_background
            case 644: // background_create_color(w,h,col)

                // TODO: This function

                return null; // background_create_color
            case 645: // background_create_gradient(w,h,col1,col2,kind)

                // TODO: This function

                return null; // background_create_gradient
            case 646: // background_create_from_screen(x,y,w,h,removeback,smooth)

                // TODO: This function

                return null; // background_create_from_screen
            case 647: // background_create_from_surface(id,x,y,w,h,removeback,smooth)

                // TODO: This function

                return null; // background_create_from_surface
            case 648: // background_delete(ind)

                // TODO: This function

                return null; // background_delete
            case 649: // background_set_alpha_from_background(ind,back)

                // TODO: This function

                return null; // background_set_alpha_from_background
            case 650: // font_add(name,size,bold,italic,first,last)

                // TODO: This function

                return null; // font_add
            case 651: // font_add_sprite(spr,first,prop,sep)

                // TODO: This function

                return null; // font_add_sprite
            case 652: // font_replace(ind,name,size,bold,italic,first,last)

                // TODO: This function

                return null; // font_replace
            case 653: // font_replace_sprite(ind,spr,first,prop,sep)

                // TODO: This function

                return null; // font_replace_sprite
            case 654: // font_delete(ind)

                // TODO: This function

                return null; // font_delete
            case 655: // path_set_kind(ind,kind)

                // TODO: This function

                return null; // path_set_kind
            case 656: // path_set_closed(ind,closed)

                // TODO: This function

                return null; // path_set_closed
            case 657: // path_set_precision(ind,prec)

                // TODO: This function

                return null; // path_set_precision
            case 658: // path_add()

                // TODO: This function

                return null; // path_add
            case 659: // path_assign(ind,path)

                // TODO: This function

                return null; // path_assign
            case 660: // path_duplicate(ind)

                // TODO: This function

                return null; // path_duplicate
            case 661: // path_append(ind,path)

                // TODO: This function

                return null; // path_append
            case 662: // path_delete(ind)

                // TODO: This function

                return null; // path_delete
            case 663: // path_add_point(ind,x,y,speed)

                // TODO: This function

                return null; // path_add_point
            case 664: // path_insert_point(ind,n,x,y,speed)

                // TODO: This function

                return null; // path_insert_point
            case 665: // path_change_point(ind,n,x,y,speed)

                // TODO: This function

                return null; // path_change_point
            case 666: // path_delete_point(ind,n)

                // TODO: This function

                return null; // path_delete_point
            case 667: // path_clear_points(ind)

                // TODO: This function

                return null; // path_clear_points
            case 668: // path_reverse(ind)

                // TODO: This function

                return null; // path_reverse
            case 669: // path_mirror(ind)

                // TODO: This function

                return null; // path_mirror
            case 670: // path_flip(ind)

                // TODO: This function

                return null; // path_flip
            case 671: // path_rotate(ind,angle)

                // TODO: This function

                return null; // path_rotate
            case 672: // path_scale(ind,xscale,yscale)

                // TODO: This function

                return null; // path_scale
            case 673: // path_shift(ind,xshift,yshift)

                // TODO: This function

                return null; // path_shift
            case 674: // execute_string(str,arg0,arg1,...)

                // TODO: This function

                return null; // execute_string
            case 675: // execute_file(fname,arg0,arg1,...)

                // TODO: This function

                return null; // execute_file
            case 676: // script_execute(ind,arg0,arg1,...)

                // TODO: This function

                return null; // script_execute
            case 677: // timeline_add()

                // TODO: This function

                return null; // timeline_add
            case 678: // timeline_delete(ind)

                // TODO: This function

                return null; // timeline_delete
            case 679: // timeline_clear(ind)

                // TODO: This function

                return null; // timeline_clear
            case 680: // timeline_moment_clear(ind,step)

                // TODO: This function

                return null; // timeline_moment_clear
            case 681: // timeline_moment_add(ind,step,codestr)

                // TODO: This function

                return null; // timeline_moment_add
            case 682: // object_set_sprite(ind,spr)

                // TODO: This function

                return null; // object_set_sprite
            case 683: // object_set_solid(ind,solid)

                // TODO: This function

                return null; // object_set_solid
            case 684: // object_set_visible(ind,vis)

                // TODO: This function

                return null; // object_set_visible
            case 685: // object_set_depth(ind,depth)

                // TODO: This function

                return null; // object_set_depth
            case 686: // object_set_persistent(ind,pers)

                // TODO: This function

                return null; // object_set_persistent
            case 687: // object_set_mask(ind,spr)

                // TODO: This function

                return null; // object_set_mask
            case 688: // object_set_parent(ind,obj)

                // TODO: This function

                return null; // object_set_parent
            case 689: // object_add()

                // TODO: This function

                return null; // object_add
            case 690: // object_delete(ind)

                // TODO: This function

                return null; // object_delete
            case 691: // object_event_clear(ind,evtype,evnumb)

                // TODO: This function

                return null; // object_event_clear
            case 692: // object_event_add(ind,evtype,evnumb,codestr)

                // TODO: This function

                return null; // object_event_add
            case 693: // room_set_width(ind,w)

                // TODO: This function

                return null; // room_set_width
            case 694: // room_set_height(ind,h)

                // TODO: This function

                return null; // room_set_height
            case 695: // room_set_caption(ind,str)

                // TODO: This function

                return null; // room_set_caption
            case 696: // room_set_persistent(ind,pers)

                // TODO: This function

                return null; // room_set_persistent
            case 697: // room_set_code(ind,codestr)

                // TODO: This function

                return null; // room_set_code
            case 698: // room_set_background_color(ind,col,show)

                // TODO: This function

                return null; // room_set_background_color
            case 699: // room_set_background(ind,bind,vis,fore,back,x,y,htiled,vtiled,hspeed,vspeed,alpha)

                // TODO: This function

                return null; // room_set_background
            case 700: // room_set_view(ind,vind,vis,xview,yview,wview,hview,xport,yport,wport,hport,hborder,vborder,hspeed,vspeed,obj)

                // TODO: This function

                return null; // room_set_view
            case 701: // room_set_view_enabled(ind,val)

                // TODO: This function

                return null; // room_set_view_enabled
            case 702: // room_add()

                // TODO: This function

                return null; // room_add
            case 703: // room_duplicate(ind)

                // TODO: This function

                return null; // room_duplicate
            case 704: // room_assign(ind,source)

                // TODO: This function

                return null; // room_assign
            case 705: // room_instance_add(ind,x,y,obj)

                // TODO: This function

                return null; // room_instance_add
            case 706: // room_instance_clear(ind)

                // TODO: This function

                return null; // room_instance_clear
            case 707: // room_tile_add(ind,back,left,top,width,height,x,y,depth)

                // TODO: This function

                return null; // room_tile_add
            case 708: // room_tile_add_ext(ind,back,left,top,width,height,x,y,depth,xscale,yscale,alpha)

                // TODO: This function

                return null; // room_tile_add_ext
            case 709: // room_tile_clear(ind)

                // TODO: This function

                return null; // room_tile_clear
            case 710: // file_text_open_read(fname)

                // TODO: This function

                return null; // file_text_open_read
            case 711: // file_text_open_write(fname)

                // TODO: This function

                return null; // file_text_open_write
            case 712: // file_text_open_append(fname)

                // TODO: This function

                return null; // file_text_open_append
            case 713: // file_text_close(file)

                // TODO: This function

                return null; // file_text_close
            case 714: // file_text_write_string(file,str)

                // TODO: This function

                return null; // file_text_write_string
            case 715: // file_text_write_real(file,val)

                // TODO: This function

                return null; // file_text_write_real
            case 716: // file_text_writeln(file)

                // TODO: This function

                return null; // file_text_writeln
            case 717: // file_text_read_string(file)

                // TODO: This function

                return null; // file_text_read_string
            case 718: // file_text_read_real(file)

                // TODO: This function

                return null; // file_text_read_real
            case 719: // file_text_readln(file)

                // TODO: This function

                return null; // file_text_readln
            case 720: // file_text_eof(file)

                // TODO: This function

                return null; // file_text_eof
            case 721: // file_text_eoln(file)

                // TODO: This function

                return null; // file_text_eoln
            case 722: // file_exists(fname)

                // TODO: This function

                return null; // file_exists
            case 723: // file_delete(fname)

                // TODO: This function

                return null; // file_delete
            case 724: // file_rename(oldname,newname)

                // TODO: This function

                return null; // file_rename
            case 725: // file_copy(fname,newname)

                // TODO: This function

                return null; // file_copy
            case 726: // directory_exists(dname)

                // TODO: This function

                return null; // directory_exists
            case 727: // directory_create(dname)

                // TODO: This function

                return null; // directory_create
            case 728: // file_find_first(mask,attr)

                // TODO: This function

                return null; // file_find_first
            case 729: // file_find_next()

                // TODO: This function

                return null; // file_find_next
            case 730: // file_find_close()

                // TODO: This function

                return null; // file_find_close
            case 731: // file_attributes(fname,attr)

                // TODO: This function

                return null; // file_attributes
            case 732: // filename_name(fname)

                // TODO: This function

                return null; // filename_name
            case 733: // filename_path(fname)

                // TODO: This function

                return null; // filename_path
            case 734: // filename_dir(fname)

                // TODO: This function

                return null; // filename_dir
            case 735: // filename_drive(fname)

                // TODO: This function

                return null; // filename_drive
            case 736: // filename_ext(fname)

                // TODO: This function

                return null; // filename_ext
            case 737: // filename_change_ext(fname,newext)

                // TODO: This function

                return null; // filename_change_ext
            case 738: // file_bin_open(fname,mode)

                // TODO: This function

                return null; // file_bin_open
            case 739: // file_bin_rewrite(file)

                // TODO: This function

                return null; // file_bin_rewrite
            case 740: // file_bin_close(file)

                // TODO: This function

                return null; // file_bin_close
            case 741: // file_bin_position(file)

                // TODO: This function

                return null; // file_bin_position
            case 742: // file_bin_size(file)

                // TODO: This function

                return null; // file_bin_size
            case 743: // file_bin_seek(file,pos)

                // TODO: This function

                return null; // file_bin_seek
            case 744: // file_bin_write_byte(file,byte)

                // TODO: This function

                return null; // file_bin_write_byte
            case 745: // file_bin_read_byte(file)

                // TODO: This function

                return null; // file_bin_read_byte
            case 746: // export_include_file(fname)

                // TODO: This function

                return null; // export_include_file
            case 747: // export_include_file_location(fname,location)

                // TODO: This function

                return null; // export_include_file_location
            case 748: // discard_include_file(fname)

                // TODO: This function

                return null; // discard_include_file
            case 749: // parameter_count()

                // TODO: This function

                return null; // parameter_count
            case 750: // parameter_string(n)

                // TODO: This function

                return null; // parameter_string
            case 751: // environment_get_variable(name)

                // TODO: This function

                return null; // environment_get_variable
            case 752: // disk_free(drive)

                // TODO: This function

                return null; // disk_free
            case 753: // disk_size(drive)

                // TODO: This function

                return null; // disk_size
            case 754: // registry_write_string(name,str)

                // TODO: This function

                return null; // registry_write_string
            case 755: // registry_write_real(name,value)

                // TODO: This function

                return null; // registry_write_real
            case 756: // registry_read_string(name)

                // TODO: This function

                return null; // registry_read_string
            case 757: // registry_read_real(name)

                // TODO: This function

                return null; // registry_read_real
            case 758: // registry_exists(name)

                // TODO: This function

                return null; // registry_exists
            case 759: // registry_write_string_ext(key,name,str)

                // TODO: This function

                return null; // registry_write_string_ext
            case 760: // registry_write_real_ext(key,name,value)

                // TODO: This function

                return null; // registry_write_real_ext
            case 761: // registry_read_string_ext(key,name)

                // TODO: This function

                return null; // registry_read_string_ext
            case 762: // registry_read_real_ext(key,name)

                // TODO: This function

                return null; // registry_read_real_ext
            case 763: // registry_exists_ext(key,name)

                // TODO: This function

                return null; // registry_exists_ext
            case 764: // registry_set_root(root)

                // TODO: This function

                return null; // registry_set_root
            case 765: // ini_open(fname)

                // TODO: This function

                return null; // ini_open
            case 766: // ini_close()

                // TODO: This function

                return null; // ini_close
            case 767: // ini_read_string(section,key,default)

                // TODO: This function

                return null; // ini_read_string
            case 768: // ini_read_real(section,key,default)

                // TODO: This function

                return null; // ini_read_real
            case 769: // ini_write_string(section,key,str)

                // TODO: This function

                return null; // ini_write_string
            case 770: // ini_write_real(section,key,value)

                // TODO: This function

                return null; // ini_write_real
            case 771: // ini_key_exists(section,key)

                // TODO: This function

                return null; // ini_key_exists
            case 772: // ini_section_exists(section)

                // TODO: This function

                return null; // ini_section_exists
            case 773: // ini_key_delete(section,key)

                // TODO: This function

                return null; // ini_key_delete
            case 774: // ini_section_delete(section)

                // TODO: This function

                return null; // ini_section_delete
            case 775: // execute_program(prog,arg,wait)

                // TODO: This function

                return null; // execute_program
            case 776: // execute_shell(prog,arg)

                // TODO: This function

                return null; // execute_shell
            case 777: // ds_set_precision(prec)

                // TODO: This function

                return null; // ds_set_precision
            case 778: // ds_stack_create()

                // TODO: This function

                return null; // ds_stack_create
            case 779: // ds_stack_destroy(id)

                // TODO: This function

                return null; // ds_stack_destroy
            case 780: // ds_stack_clear(id)

                // TODO: This function

                return null; // ds_stack_clear
            case 781: // ds_stack_copy(id,source)

                // TODO: This function

                return null; // ds_stack_copy
            case 782: // ds_stack_size(id)

                // TODO: This function

                return null; // ds_stack_size
            case 783: // ds_stack_empty(id)

                // TODO: This function

                return null; // ds_stack_empty
            case 784: // ds_stack_push(id,value)

                // TODO: This function

                return null; // ds_stack_push
            case 785: // ds_stack_pop(id)

                // TODO: This function

                return null; // ds_stack_pop
            case 786: // ds_stack_top(id)

                // TODO: This function

                return null; // ds_stack_top
            case 787: // ds_stack_write(id)

                // TODO: This function

                return null; // ds_stack_write
            case 788: // ds_stack_read(id,str)

                // TODO: This function

                return null; // ds_stack_read
            case 789: // ds_queue_create()

                // TODO: This function

                return null; // ds_queue_create
            case 790: // ds_queue_destroy(id)

                // TODO: This function

                return null; // ds_queue_destroy
            case 791: // ds_queue_clear(id)

                // TODO: This function

                return null; // ds_queue_clear
            case 792: // ds_queue_copy(id,source)

                // TODO: This function

                return null; // ds_queue_copy
            case 793: // ds_queue_size(id)

                // TODO: This function

                return null; // ds_queue_size
            case 794: // ds_queue_empty(id)

                // TODO: This function

                return null; // ds_queue_empty
            case 795: // ds_queue_enqueue(id,value)

                // TODO: This function

                return null; // ds_queue_enqueue
            case 796: // ds_queue_dequeue(id)

                // TODO: This function

                return null; // ds_queue_dequeue
            case 797: // ds_queue_head(id)

                // TODO: This function

                return null; // ds_queue_head
            case 798: // ds_queue_tail(id)

                // TODO: This function

                return null; // ds_queue_tail
            case 799: // ds_queue_write(id)

                // TODO: This function

                return null; // ds_queue_write
            case 800: // ds_queue_read(id,str)

                // TODO: This function

                return null; // ds_queue_read
            case 801: // ds_list_create()

                // TODO: This function

                return null; // ds_list_create
            case 802: // ds_list_destroy(id)

                // TODO: This function

                return null; // ds_list_destroy
            case 803: // ds_list_clear(id)

                // TODO: This function

                return null; // ds_list_clear
            case 804: // ds_list_copy(id,source)

                // TODO: This function

                return null; // ds_list_copy
            case 805: // ds_list_size(id)

                // TODO: This function

                return null; // ds_list_size
            case 806: // ds_list_empty(id)

                // TODO: This function

                return null; // ds_list_empty
            case 807: // ds_list_add(id,value)

                // TODO: This function

                return null; // ds_list_add
            case 808: // ds_list_insert(id,pos,value)

                // TODO: This function

                return null; // ds_list_insert
            case 809: // ds_list_replace(id,pos,value)

                // TODO: This function

                return null; // ds_list_replace
            case 810: // ds_list_delete(id,pos)

                // TODO: This function

                return null; // ds_list_delete
            case 811: // ds_list_find_index(id,value)

                // TODO: This function

                return null; // ds_list_find_index
            case 812: // ds_list_find_value(id,pos)

                // TODO: This function

                return null; // ds_list_find_value
            case 813: // ds_list_sort(id,ascending)

                // TODO: This function

                return null; // ds_list_sort
            case 814: // ds_list_shuffle(id)

                // TODO: This function

                return null; // ds_list_shuffle
            case 815: // ds_list_write(id)

                // TODO: This function

                return null; // ds_list_write
            case 816: // ds_list_read(id,str)

                // TODO: This function

                return null; // ds_list_read
            case 817: // ds_map_create()

                // TODO: This function

                return null; // ds_map_create
            case 818: // ds_map_destroy(id)

                // TODO: This function

                return null; // ds_map_destroy
            case 819: // ds_map_clear(id)

                // TODO: This function

                return null; // ds_map_clear
            case 820: // ds_map_copy(id,source)

                // TODO: This function

                return null; // ds_map_copy
            case 821: // ds_map_size(id)

                // TODO: This function

                return null; // ds_map_size
            case 822: // ds_map_empty(id)

                // TODO: This function

                return null; // ds_map_empty
            case 823: // ds_map_add(id,key,value)

                // TODO: This function

                return null; // ds_map_add
            case 824: // ds_map_replace(id,key,value)

                // TODO: This function

                return null; // ds_map_replace
            case 825: // ds_map_delete(id,key)

                // TODO: This function

                return null; // ds_map_delete
            case 826: // ds_map_exists(id,key)

                // TODO: This function

                return null; // ds_map_exists
            case 827: // ds_map_find_value(id,key)

                // TODO: This function

                return null; // ds_map_find_value
            case 828: // ds_map_find_previous(id,key)

                // TODO: This function

                return null; // ds_map_find_previous
            case 829: // ds_map_find_next(id,key)

                // TODO: This function

                return null; // ds_map_find_next
            case 830: // ds_map_find_first(id)

                // TODO: This function

                return null; // ds_map_find_first
            case 831: // ds_map_find_last(id)

                // TODO: This function

                return null; // ds_map_find_last
            case 832: // ds_map_write(id)

                // TODO: This function

                return null; // ds_map_write
            case 833: // ds_map_read(id,str)

                // TODO: This function

                return null; // ds_map_read
            case 834: // ds_priority_create()

                // TODO: This function

                return null; // ds_priority_create
            case 835: // ds_priority_destroy(id)

                // TODO: This function

                return null; // ds_priority_destroy
            case 836: // ds_priority_clear(id)

                // TODO: This function

                return null; // ds_priority_clear
            case 837: // ds_priority_copy(id,source)

                // TODO: This function

                return null; // ds_priority_copy
            case 838: // ds_priority_size(id)

                // TODO: This function

                return null; // ds_priority_size
            case 839: // ds_priority_empty(id)

                // TODO: This function

                return null; // ds_priority_empty
            case 840: // ds_priority_add(id,value,priority)

                // TODO: This function

                return null; // ds_priority_add
            case 841: // ds_priority_change_priority(id,value,priority)

                // TODO: This function

                return null; // ds_priority_change_priority
            case 842: // ds_priority_find_priority(id,value)

                // TODO: This function

                return null; // ds_priority_find_priority
            case 843: // ds_priority_delete_value(id,value)

                // TODO: This function

                return null; // ds_priority_delete_value
            case 844: // ds_priority_delete_min(id)

                // TODO: This function

                return null; // ds_priority_delete_min
            case 845: // ds_priority_find_min(id)

                // TODO: This function

                return null; // ds_priority_find_min
            case 846: // ds_priority_delete_max(id)

                // TODO: This function

                return null; // ds_priority_delete_max
            case 847: // ds_priority_find_max(id)

                // TODO: This function

                return null; // ds_priority_find_max
            case 848: // ds_priority_write(id)

                // TODO: This function

                return null; // ds_priority_write
            case 849: // ds_priority_read(id,str)

                // TODO: This function

                return null; // ds_priority_read
            case 850: // ds_grid_create(w,h)

                // TODO: This function

                return null; // ds_grid_create
            case 851: // ds_grid_destroy(id)

                // TODO: This function

                return null; // ds_grid_destroy
            case 852: // ds_grid_copy(id,source)

                // TODO: This function

                return null; // ds_grid_copy
            case 853: // ds_grid_resize(id,w,h)

                // TODO: This function

                return null; // ds_grid_resize
            case 854: // ds_grid_width(id)

                // TODO: This function

                return null; // ds_grid_width
            case 855: // ds_grid_height(id)

                // TODO: This function

                return null; // ds_grid_height
            case 856: // ds_grid_clear(id,val)

                // TODO: This function

                return null; // ds_grid_clear
            case 857: // ds_grid_set(id,x,y,val)

                // TODO: This function

                return null; // ds_grid_set
            case 858: // ds_grid_add(id,x,y,val)

                // TODO: This function

                return null; // ds_grid_add
            case 859: // ds_grid_multiply(id,x,y,val)

                // TODO: This function

                return null; // ds_grid_multiply
            case 860: // ds_grid_set_region(id,x1,y1,x2,y2,val)

                // TODO: This function

                return null; // ds_grid_set_region
            case 861: // ds_grid_add_region(id,x1,y1,x2,y2,val)

                // TODO: This function

                return null; // ds_grid_add_region
            case 862: // ds_grid_multiply_region(id,x1,y1,x2,y2,val)

                // TODO: This function

                return null; // ds_grid_multiply_region
            case 863: // ds_grid_set_disk(id,xm,ym,r,val)

                // TODO: This function

                return null; // ds_grid_set_disk
            case 864: // ds_grid_add_disk(id,xm,ym,r,val)

                // TODO: This function

                return null; // ds_grid_add_disk
            case 865: // ds_grid_multiply_disk(id,xm,ym,r,val)

                // TODO: This function

                return null; // ds_grid_multiply_disk
            case 866: // ds_grid_set_grid_region(id,source,x1,y1,x2,y2,xpos,ypos)

                // TODO: This function

                return null; // ds_grid_set_grid_region
            case 867: // ds_grid_add_grid_region(id,source,x1,y1,x2,y2,xpos,ypos)

                // TODO: This function

                return null; // ds_grid_add_grid_region
            case 868: // ds_grid_multiply_grid_region(id,source,x1,y1,x2,y2,xpos,ypos)

                // TODO: This function

                return null; // ds_grid_multiply_grid_region
            case 869: // ds_grid_get(id,x,y)

                // TODO: This function

                return null; // ds_grid_get
            case 870: // ds_grid_get_sum(id,x1,y1,x2,y2)

                // TODO: This function

                return null; // ds_grid_get_sum
            case 871: // ds_grid_get_max(id,x1,y1,x2,y2)

                // TODO: This function

                return null; // ds_grid_get_max
            case 872: // ds_grid_get_min(id,x1,y1,x2,y2)

                // TODO: This function

                return null; // ds_grid_get_min
            case 873: // ds_grid_get_mean(id,x1,y1,x2,y2)

                // TODO: This function

                return null; // ds_grid_get_mean
            case 874: // ds_grid_get_disk_sum(id,xm,ym,r)

                // TODO: This function

                return null; // ds_grid_get_disk_sum
            case 875: // ds_grid_get_disk_min(id,xm,ym,r)

                // TODO: This function

                return null; // ds_grid_get_disk_min
            case 876: // ds_grid_get_disk_max(id,xm,ym,r)

                // TODO: This function

                return null; // ds_grid_get_disk_max
            case 877: // ds_grid_get_disk_mean(id,xm,ym,r)

                // TODO: This function

                return null; // ds_grid_get_disk_mean
            case 878: // ds_grid_value_exists(id,x1,y1,x2,y2,val)

                // TODO: This function

                return null; // ds_grid_value_exists
            case 879: // ds_grid_value_x(id,x1,y1,x2,y2,val)

                // TODO: This function

                return null; // ds_grid_value_x
            case 880: // ds_grid_value_y(id,x1,y1,x2,y2,val)

                // TODO: This function

                return null; // ds_grid_value_y
            case 881: // ds_grid_value_disk_exists(id,xm,ym,r,val)

                // TODO: This function

                return null; // ds_grid_value_disk_exists
            case 882: // ds_grid_value_disk_x(id,xm,ym,r,val)

                // TODO: This function

                return null; // ds_grid_value_disk_x
            case 883: // ds_grid_value_disk_y(id,xm,ym,r,val)

                // TODO: This function

                return null; // ds_grid_value_disk_y
            case 884: // ds_grid_shuffle(id)

                // TODO: This function

                return null; // ds_grid_shuffle
            case 885: // ds_grid_write(id)

                // TODO: This function

                return null; // ds_grid_write
            case 886: // ds_grid_read(id,str)

                // TODO: This function

                return null; // ds_grid_read
            case 887: // effect_create_below(kind,x,y,size,color)

                // TODO: This function

                return null; // effect_create_below
            case 888: // effect_create_above(kind,x,y,size,color)

                // TODO: This function

                return null; // effect_create_above
            case 889: // effect_clear()

                // TODO: This function

                return null; // effect_clear
            case 890: // part_type_create()

                // TODO: This function

                return null; // part_type_create
            case 891: // part_type_destroy(ind)

                // TODO: This function

                return null; // part_type_destroy
            case 892: // part_type_exists(ind)

                // TODO: This function

                return null; // part_type_exists
            case 893: // part_type_clear(ind)

                // TODO: This function

                return null; // part_type_clear
            case 894: // part_type_shape(ind,shape)

                // TODO: This function

                return null; // part_type_shape
            case 895: // part_type_sprite(ind,sprite,animat,stretch,random)

                // TODO: This function

                return null; // part_type_sprite
            case 896: // part_type_size(ind,size_min,size_max,size_incr,size_wiggle)

                // TODO: This function

                return null; // part_type_size
            case 897: // part_type_scale(ind,xscale,yscale)

                // TODO: This function

                return null; // part_type_scale
            case 898: // part_type_orientation(ind,ang_min,ang_max,ang_incr,ang_wiggle,ang_relative)

                // TODO: This function

                return null; // part_type_orientation
            case 899: // part_type_life(ind,life_min,life_max)

                // TODO: This function

                return null; // part_type_life
            case 900: // part_type_step(ind,step_number,step_type)

                // TODO: This function

                return null; // part_type_step
            case 901: // part_type_death(ind,death_number,death_type)

                // TODO: This function

                return null; // part_type_death
            case 902: // part_type_speed(ind,speed_min,speed_max,speed_incr,speed_wiggle)

                // TODO: This function

                return null; // part_type_speed
            case 903: // part_type_direction(ind,dir_min,dir_max,dir_incr,dir_wiggle)

                // TODO: This function

                return null; // part_type_direction
            case 904: // part_type_gravity(ind,grav_amount,grav_dir)

                // TODO: This function

                return null; // part_type_gravity
            case 905: // part_type_color1(ind,color1)

                // TODO: This function

                return null; // part_type_color1
            case 906: // part_type_color2(ind,color1,color2)

                // TODO: This function

                return null; // part_type_color2
            case 907: // part_type_color3(ind,color1,color2,color3)

                // TODO: This function

                return null; // part_type_color3
            case 908: // part_type_color_mix(ind,color1,color2)

                // TODO: This function

                return null; // part_type_color_mix
            case 909: // part_type_color_rgb(ind,rmin,rmax,gmin,gmax,bmin,bmax)

                // TODO: This function

                return null; // part_type_color_rgb
            case 910: // part_type_color_hsv(ind,hmin,hmax,smin,smax,vmin,vmax)

                // TODO: This function

                return null; // part_type_color_hsv
            case 911: // part_type_alpha1(ind,alpha1)

                // TODO: This function

                return null; // part_type_alpha1
            case 912: // part_type_alpha2(ind,alpha1,alpha2)

                // TODO: This function

                return null; // part_type_alpha2
            case 913: // part_type_alpha3(ind,alpha1,alpha2,alpha3)

                // TODO: This function

                return null; // part_type_alpha3
            case 914: // part_type_blend(ind,additive)

                // TODO: This function

                return null; // part_type_blend
            case 915: // part_system_create()

                // TODO: This function

                return null; // part_system_create
            case 916: // part_system_destroy(ind)

                // TODO: This function

                return null; // part_system_destroy
            case 917: // part_system_exists(ind)

                // TODO: This function

                return null; // part_system_exists
            case 918: // part_system_clear(ind)

                // TODO: This function

                return null; // part_system_clear
            case 919: // part_system_draw_order(ind,oldtonew)

                // TODO: This function

                return null; // part_system_draw_order
            case 920: // part_system_depth(ind,depth)

                // TODO: This function

                return null; // part_system_depth
            case 921: // part_system_position(ind,x,y)

                // TODO: This function

                return null; // part_system_position
            case 922: // part_system_automatic_update(ind,automatic)

                // TODO: This function

                return null; // part_system_automatic_update
            case 923: // part_system_automatic_draw(ind,draw)

                // TODO: This function

                return null; // part_system_automatic_draw
            case 924: // part_system_update(ind)

                // TODO: This function

                return null; // part_system_update
            case 925: // part_system_drawit(ind)

                // TODO: This function

                return null; // part_system_drawit
            case 926: // part_particles_create(ind,x,y,parttype,number)

                // TODO: This function

                return null; // part_particles_create
            case 927: // part_particles_create_color(ind,x,y,parttype,color,number)

                // TODO: This function

                return null; // part_particles_create_color
            case 928: // part_particles_clear(ind)

                // TODO: This function

                return null; // part_particles_clear
            case 929: // part_particles_count(ind)

                // TODO: This function

                return null; // part_particles_count
            case 930: // part_emitter_create(ps)

                // TODO: This function

                return null; // part_emitter_create
            case 931: // part_emitter_destroy(ps,ind)

                // TODO: This function

                return null; // part_emitter_destroy
            case 932: // part_emitter_destroy_all(ps)

                // TODO: This function

                return null; // part_emitter_destroy_all
            case 933: // part_emitter_exists(ps,ind)

                // TODO: This function

                return null; // part_emitter_exists
            case 934: // part_emitter_clear(ps,ind)

                // TODO: This function

                return null; // part_emitter_clear
            case 935: // part_emitter_region(ps,ind,xmin,xmax,ymin,ymax,shape,distribution)

                // TODO: This function

                return null; // part_emitter_region
            case 936: // part_emitter_burst(ps,ind,parttype,number)

                // TODO: This function

                return null; // part_emitter_burst
            case 937: // part_emitter_stream(ps,ind,parttype,number)

                // TODO: This function

                return null; // part_emitter_stream
            case 938: // part_attractor_create(ps)

                // TODO: This function

                return null; // part_attractor_create
            case 939: // part_attractor_destroy(ps,ind)

                // TODO: This function

                return null; // part_attractor_destroy
            case 940: // part_attractor_destroy_all(ps)

                // TODO: This function

                return null; // part_attractor_destroy_all
            case 941: // part_attractor_exists(ps,ind)

                // TODO: This function

                return null; // part_attractor_exists
            case 942: // part_attractor_clear(ps,ind)

                // TODO: This function

                return null; // part_attractor_clear
            case 943: // part_attractor_position(ps,ind,x,y)

                // TODO: This function

                return null; // part_attractor_position
            case 944: // part_attractor_force(ps,ind,force,dist,kind,additive)

                // TODO: This function

                return null; // part_attractor_force
            case 945: // part_destroyer_create(ps)

                // TODO: This function

                return null; // part_destroyer_create
            case 946: // part_destroyer_destroy(ps,ind)

                // TODO: This function

                return null; // part_destroyer_destroy
            case 947: // part_destroyer_destroy_all(ps)

                // TODO: This function

                return null; // part_destroyer_destroy_all
            case 948: // part_destroyer_exists(ps,ind)

                // TODO: This function

                return null; // part_destroyer_exists
            case 949: // part_destroyer_clear(ps,ind)

                // TODO: This function

                return null; // part_destroyer_clear
            case 950: // part_destroyer_region(ps,ind,xmin,xmax,ymin,ymax,shape)

                // TODO: This function

                return null; // part_destroyer_region
            case 951: // part_deflector_create(ps)

                // TODO: This function

                return null; // part_deflector_create
            case 952: // part_deflector_destroy(ps,ind)

                // TODO: This function

                return null; // part_deflector_destroy
            case 953: // part_deflector_destroy_all(ps)

                // TODO: This function

                return null; // part_deflector_destroy_all
            case 954: // part_deflector_exists(ps,ind)

                // TODO: This function

                return null; // part_deflector_exists
            case 955: // part_deflector_clear(ps,ind)

                // TODO: This function

                return null; // part_deflector_clear
            case 956: // part_deflector_region(ps,ind,xmin,xmax,ymin,ymax)

                // TODO: This function

                return null; // part_deflector_region
            case 957: // part_deflector_kind(ps,ind,kind)

                // TODO: This function

                return null; // part_deflector_kind
            case 958: // part_deflector_friction(ps,ind,amount)

                // TODO: This function

                return null; // part_deflector_friction
            case 959: // part_changer_create(ps)

                // TODO: This function

                return null; // part_changer_create
            case 960: // part_changer_destroy(ps,ind)

                // TODO: This function

                return null; // part_changer_destroy
            case 961: // part_changer_destroy_all(ps)

                // TODO: This function

                return null; // part_changer_destroy_all
            case 962: // part_changer_exists(ps,ind)

                // TODO: This function

                return null; // part_changer_exists
            case 963: // part_changer_clear(ps,ind)

                // TODO: This function

                return null; // part_changer_clear
            case 964: // part_changer_region(ps,ind,xmin,xmax,ymin,ymax,shape)

                // TODO: This function

                return null; // part_changer_region
            case 965: // part_changer_kind(ps,ind,kind)

                // TODO: This function

                return null; // part_changer_kind
            case 966: // part_changer_types(ps,ind,parttype1,parttype2)

                // TODO: This function

                return null; // part_changer_types
            case 967: // mplay_init_ipx()

                // TODO: This function

                return null; // mplay_init_ipx
            case 968: // mplay_init_tcpip(addrstring)

                // TODO: This function

                return null; // mplay_init_tcpip
            case 969: // mplay_init_modem(initstr,phonenr)

                // TODO: This function

                return null; // mplay_init_modem
            case 970: // mplay_init_serial(portno,baudrate,stopbits,parity,flow)

                // TODO: This function

                return null; // mplay_init_serial
            case 971: // mplay_connect_status()

                // TODO: This function

                return null; // mplay_connect_status
            case 972: // mplay_end()

                // TODO: This function

                return null; // mplay_end
            case 973: // mplay_ipaddress()

                // TODO: This function

                return null; // mplay_ipaddress
            case 974: // mplay_session_mode(move)

                // TODO: This function

                return null; // mplay_session_mode
            case 975: // mplay_session_create(sesname,playnumb,playername)

                // TODO: This function

                return null; // mplay_session_create
            case 976: // mplay_session_find()

                // TODO: This function

                return null; // mplay_session_find
            case 977: // mplay_session_name(numb)

                // TODO: This function

                return null; // mplay_session_name
            case 978: // mplay_session_join(numb,playername)

                // TODO: This function

                return null; // mplay_session_join
            case 979: // mplay_session_status()

                // TODO: This function

                return null; // mplay_session_status
            case 980: // mplay_session_end()

                // TODO: This function

                return null; // mplay_session_end
            case 981: // mplay_player_find()

                // TODO: This function

                return null; // mplay_player_find
            case 982: // mplay_player_name(numb)

                // TODO: This function

                return null; // mplay_player_name
            case 983: // mplay_player_id(numb)

                // TODO: This function

                return null; // mplay_player_id
            case 984: // mplay_data_write(ind,value)

                // TODO: This function

                return null; // mplay_data_write
            case 985: // mplay_data_read(ind)

                // TODO: This function

                return null; // mplay_data_read
            case 986: // mplay_data_mode(guaranteed)

                // TODO: This function

                return null; // mplay_data_mode
            case 987: // mplay_message_send(player,id,val)

                // TODO: This function

                return null; // mplay_message_send
            case 988: // mplay_message_send_guaranteed(player,id,val)

                // TODO: This function

                return null; // mplay_message_send_guaranteed
            case 989: // mplay_message_receive(player)

                // TODO: This function

                return null; // mplay_message_receive
            case 990: // mplay_message_id()

                // TODO: This function

                return null; // mplay_message_id
            case 991: // mplay_message_value()

                // TODO: This function

                return null; // mplay_message_value
            case 992: // mplay_message_player()

                // TODO: This function

                return null; // mplay_message_player
            case 993: // mplay_message_name()

                // TODO: This function

                return null; // mplay_message_name
            case 994: // mplay_message_count(player)

                // TODO: This function

                return null; // mplay_message_count
            case 995: // mplay_message_clear(player)

                // TODO: This function

                return null; // mplay_message_clear
            case 996: // external_call(id,arg1,arg2,...)

                // TODO: This function

                return null; // external_call
            case 997: // external_define(dll,name,calltype,restype,argnumb,arg1type,arg2type,...)

                // TODO: This function

                return null; // external_define
            case 998: // external_free(dllname)

                // TODO: This function

                return null; // external_free
            case 999: // get_function_address(function_name)

                // TODO: This function

                return null; // get_function_address
            case 1000: // window_handle()

                // TODO: This function

                return null; // window_handle
            case 1001: // d3d_start()

                // TODO: This function

                return null; // d3d_start
            case 1002: // d3d_end()

                // TODO: This function

                return null; // d3d_end
            case 1003: // d3d_set_hidden(enable)

                // TODO: This function

                return null; // d3d_set_hidden
            case 1004: // d3d_set_perspective(enable)

                // TODO: This function

                return null; // d3d_set_perspective
            case 1005: // d3d_set_depth(depth)

                // TODO: This function

                return null; // d3d_set_depth
            case 1006: // d3d_set_zwriteenable(on_off)

                // TODO: This function

                return null; // d3d_set_zwriteenable
            case 1007: // d3d_primitive_begin(kind)

                // TODO: This function

                return null; // d3d_primitive_begin
            case 1008: // d3d_vertex(x,y,z)

                // TODO: This function

                return null; // d3d_vertex
            case 1009: // d3d_vertex_color(x,y,z,col,alpha)

                // TODO: This function

                return null; // d3d_vertex_color
            case 1010: // d3d_primitive_end()

                // TODO: This function

                return null; // d3d_primitive_end
            case 1011: // d3d_primitive_begin_texture(kind,texid)

                // TODO: This function

                return null; // d3d_primitive_begin_texture
            case 1012: // d3d_vertex_texture(x,y,z,xtex,ytex)

                // TODO: This function

                return null; // d3d_vertex_texture
            case 1013: // d3d_vertex_texture_color(x,y,z,xtex,ytex,col,alpha)

                // TODO: This function

                return null; // d3d_vertex_texture_color
            case 1014: // d3d_draw_block(x1,y1,z1,x2,y2,z2,texid,hrepeat,vrepeat)

                // TODO: This function

                return null; // d3d_draw_block
            case 1015: // d3d_draw_cylinder(x1,y1,z1,x2,y2,z2,texid,hrepeat,vrepeat,closed,steps)

                // TODO: This function

                return null; // d3d_draw_cylinder
            case 1016: // d3d_draw_cone(x1,y1,z1,x2,y2,z2,texid,hrepeat,vrepeat,closed,steps)

                // TODO: This function

                return null; // d3d_draw_cone
            case 1017: // d3d_draw_ellipsoid(x1,y1,z1,x2,y2,z2,texid,hrepeat,vrepeat,steps)

                // TODO: This function

                return null; // d3d_draw_ellipsoid
            case 1018: // d3d_draw_wall(x1,y1,z1,x2,y2,z2,texid,hrepeat,vrepeat)

                // TODO: This function

                return null; // d3d_draw_wall
            case 1019: // d3d_draw_floor(x1,y1,z1,x2,y2,z2,texid,hrepeat,vrepeat)

                // TODO: This function

                return null; // d3d_draw_floor
            case 1020: // d3d_set_projection(xfrom,yfrom,zfrom,xto,yto,zto,xup,yup,zup)

                // TODO: This function

                return null; // d3d_set_projection
            case 1021: // d3d_set_projection_ext(xfrom,yfrom,zfrom,xto,yto,zto,xup,yup,zup,angle,aspect,znear,zfar)

                // TODO: This function

                return null; // d3d_set_projection_ext
            case 1022: // d3d_set_projection_ortho(x,y,w,h,angle)

                // TODO: This function

                return null; // d3d_set_projection_ortho
            case 1023: // d3d_set_projection_perspective(x,y,w,h,angle)

                // TODO: This function

                return null; // d3d_set_projection_perspective
            case 1024: // d3d_transform_set_identity()

                // TODO: This function

                return null; // d3d_transform_set_identity
            case 1025: // d3d_transform_set_translation(xt,yt,zt)

                // TODO: This function

                return null; // d3d_transform_set_translation
            case 1026: // d3d_transform_set_scaling(xs,ys,zs)

                // TODO: This function

                return null; // d3d_transform_set_scaling
            case 1027: // d3d_transform_set_rotation_x(angle)

                // TODO: This function

                return null; // d3d_transform_set_rotation_x
            case 1028: // d3d_transform_set_rotation_y(angle)

                // TODO: This function

                return null; // d3d_transform_set_rotation_y
            case 1029: // d3d_transform_set_rotation_z(angle)

                // TODO: This function

                return null; // d3d_transform_set_rotation_z
            case 1030: // d3d_transform_set_rotation_axis(xa,ya,za,angle)

                // TODO: This function

                return null; // d3d_transform_set_rotation_axis
            case 1031: // d3d_transform_add_translation(xt,yt,zt)

                // TODO: This function

                return null; // d3d_transform_add_translation
            case 1032: // d3d_transform_add_scaling(xs,ys,zs)

                // TODO: This function

                return null; // d3d_transform_add_scaling
            case 1033: // d3d_transform_add_rotation_x(angle)

                // TODO: This function

                return null; // d3d_transform_add_rotation_x
            case 1034: // d3d_transform_add_rotation_y(angle)

                // TODO: This function

                return null; // d3d_transform_add_rotation_y
            case 1035: // d3d_transform_add_rotation_z(angle)

                // TODO: This function

                return null; // d3d_transform_add_rotation_z
            case 1036: // d3d_transform_add_rotation_axis(xa,ya,za,angle)

                // TODO: This function

                return null; // d3d_transform_add_rotation_axis
            case 1037: // d3d_transform_stack_clear()

                // TODO: This function

                return null; // d3d_transform_stack_clear
            case 1038: // d3d_transform_stack_empty()

                // TODO: This function

                return null; // d3d_transform_stack_empty
            case 1039: // d3d_transform_stack_push()

                // TODO: This function

                return null; // d3d_transform_stack_push
            case 1040: // d3d_transform_stack_pop()

                // TODO: This function

                return null; // d3d_transform_stack_pop
            case 1041: // d3d_transform_stack_top()

                // TODO: This function

                return null; // d3d_transform_stack_top
            case 1042: // d3d_transform_stack_discard()

                // TODO: This function

                return null; // d3d_transform_stack_discard
            case 1043: // d3d_set_fog(enable,color,start,end)

                // TODO: This function

                return null; // d3d_set_fog
            case 1044: // d3d_set_lighting(enable)

                // TODO: This function

                return null; // d3d_set_lighting
            case 1045: // d3d_set_shading(smooth)

                // TODO: This function

                return null; // d3d_set_shading
            case 1046: // d3d_set_culling(cull)

                // TODO: This function

                return null; // d3d_set_culling
            case 1047: // d3d_light_define_ambient( col )

                // TODO: This function

                return null; // d3d_light_define_ambient
            case 1048: // d3d_light_define_direction(ind,dx,dy,dz,col)

                // TODO: This function

                return null; // d3d_light_define_direction
            case 1049: // d3d_light_define_point(ind,x,y,z,range,col)

                // TODO: This function

                return null; // d3d_light_define_point
            case 1050: // d3d_light_enable(ind,enable)

                // TODO: This function

                return null; // d3d_light_enable
            case 1051: // d3d_vertex_normal(x,y,z,nx,ny,nz)

                // TODO: This function

                return null; // d3d_vertex_normal
            case 1052: // d3d_vertex_normal_color(x,y,z,nx,ny,nz,col,alpha)

                // TODO: This function

                return null; // d3d_vertex_normal_color
            case 1053: // d3d_vertex_normal_texture(x,y,z,nx,ny,nz,xtex,ytex)

                // TODO: This function

                return null; // d3d_vertex_normal_texture
            case 1054: // d3d_vertex_normal_texture_color(x,y,z,nx,ny,nz,xtex,ytex,col,alpha)

                // TODO: This function

                return null; // d3d_vertex_normal_texture_color
            case 1055: // d3d_model_create()

                // TODO: This function

                return null; // d3d_model_create
            case 1056: // d3d_model_destroy(ind)

                // TODO: This function

                return null; // d3d_model_destroy
            case 1057: // d3d_model_clear(ind)

                // TODO: This function

                return null; // d3d_model_clear
            case 1058: // d3d_model_save(ind,fname)

                // TODO: This function

                return null; // d3d_model_save
            case 1059: // d3d_model_load(ind,fname)

                // TODO: This function

                return null; // d3d_model_load
            case 1060: // d3d_model_draw(ind,x,y,z,texid)

                // TODO: This function

                return null; // d3d_model_draw
            case 1061: // d3d_model_primitive_begin(ind,kind)

                // TODO: This function

                return null; // d3d_model_primitive_begin
            case 1062: // d3d_model_vertex(ind,x,y,z)

                // TODO: This function

                return null; // d3d_model_vertex
            case 1063: // d3d_model_vertex_color(ind,x,y,z,col,alpha)

                // TODO: This function

                return null; // d3d_model_vertex_color
            case 1064: // d3d_model_vertex_texture(ind,x,y,z,xtex,ytex)

                // TODO: This function

                return null; // d3d_model_vertex_texture
            case 1065: // d3d_model_vertex_texture_color(ind,x,y,z,xtex,ytex,col,alpha)

                // TODO: This function

                return null; // d3d_model_vertex_texture_color
            case 1066: // d3d_model_vertex_normal(ind,x,y,z,nx,ny,nz)

                // TODO: This function

                return null; // d3d_model_vertex_normal
            case 1067: // d3d_model_vertex_normal_color(ind,x,y,z,nx,ny,nz,col,alpha)

                // TODO: This function

                return null; // d3d_model_vertex_normal_color
            case 1068: // d3d_model_vertex_normal_texture(ind,x,y,z,nx,ny,nz,xtex,ytex)

                // TODO: This function

                return null; // d3d_model_vertex_normal_texture
            case 1069: // d3d_model_vertex_normal_texture_color(ind,x,y,z,nx,ny,nz,xtex,ytex,col,alpha)

                // TODO: This function

                return null; // d3d_model_vertex_normal_texture_color
            case 1070: // d3d_model_primitive_end(ind)

                // TODO: This function

                return null; // d3d_model_primitive_end
            case 1071: // d3d_model_block(ind,x1,y1,z1,x2,y2,z2,hrepeat,vrepeat)

                // TODO: This function

                return null; // d3d_model_block
            case 1072: // d3d_model_cylinder(ind,x1,y1,z1,x2,y2,z2,hrepeat,vrepeat,closed,steps)

                // TODO: This function

                return null; // d3d_model_cylinder
            case 1073: // d3d_model_cone(ind,x1,y1,z1,x2,y2,z2,hrepeat,vrepeat,closed,steps)

                // TODO: This function

                return null; // d3d_model_cone
            case 1074: // d3d_model_ellipsoid(ind,x1,y1,z1,x2,y2,z2,hrepeat,vrepeat,steps)

                // TODO: This function

                return null; // d3d_model_ellipsoid
            case 1075: // d3d_model_wall(ind,x1,y1,z1,x2,y2,z2,hrepeat,vrepeat)

                // TODO: This function

                return null; // d3d_model_wall
            case 1076: // d3d_model_floor(ind,x1,y1,z1,x2,y2,z2,hrepeat,vrepeat)

                // TODO: This function

                return null; // d3d_model_floor

        }
        return null;
    }

    private static double[] getDoubles(Constant[] args) {
        double[] d = new double[args.length];
        int i = 0;
        for (Constant c : args)
            d[i++] = c.dVal;
        return d;
    }

    // TODO: this function
    private static void checkString(Constant c) {

    }

    // TODO: this function
    private static void checkReal(Constant c) {
        if (!c.isReal)
            ; // who cares
    }
}
