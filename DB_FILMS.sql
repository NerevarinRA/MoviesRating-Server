PGDMP  "    1                |            test_ck_film    16.1    16.1     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16398    test_ck_film    DATABASE     �   CREATE DATABASE test_ck_film WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Russian_Russia.1251';
    DROP DATABASE test_ck_film;
                postgres    false            �            1259    16461    comment    TABLE     �   CREATE TABLE public.comment (
    id_comment integer NOT NULL,
    text text NOT NULL,
    "user" character varying NOT NULL,
    id_film integer NOT NULL
);
    DROP TABLE public.comment;
       public         heap    postgres    false            �            1259    16459    comment_id_comment_seq    SEQUENCE     �   CREATE SEQUENCE public.comment_id_comment_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.comment_id_comment_seq;
       public          postgres    false    218            �           0    0    comment_id_comment_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.comment_id_comment_seq OWNED BY public.comment.id_comment;
          public          postgres    false    217            �            1259    16399    film    TABLE     h  CREATE TABLE public.film (
    name_film character varying(35) NOT NULL,
    id_film integer NOT NULL,
    rating real,
    year character varying(4),
    director character varying(25),
    styles character varying,
    poster bytea,
    annotation text,
    styles_array character varying[],
    wallpaper character varying,
    number_of_ratings integer
);
    DROP TABLE public.film;
       public         heap    postgres    false            �            1259    16502    film_comment    TABLE     d   CREATE TABLE public.film_comment (
    id_comment integer NOT NULL,
    id_film integer NOT NULL
);
     DROP TABLE public.film_comment;
       public         heap    postgres    false            �            1259    24616    user_rating    TABLE     �   CREATE TABLE public.user_rating (
    user_name character varying(10) NOT NULL,
    id_film integer NOT NULL,
    user_rating real NOT NULL
);
    DROP TABLE public.user_rating;
       public         heap    postgres    false            �            1259    16406    users    TABLE     �   CREATE TABLE public.users (
    avatar bytea,
    user_name character varying(10) NOT NULL,
    email character varying NOT NULL,
    passwd character varying(512) NOT NULL,
    collection integer[]
);
    DROP TABLE public.users;
       public         heap    postgres    false            *           2604    16464    comment id_comment    DEFAULT     x   ALTER TABLE ONLY public.comment ALTER COLUMN id_comment SET DEFAULT nextval('public.comment_id_comment_seq'::regclass);
 A   ALTER TABLE public.comment ALTER COLUMN id_comment DROP DEFAULT;
       public          postgres    false    217    218    218            �          0    16461    comment 
   TABLE DATA           D   COPY public.comment (id_comment, text, "user", id_film) FROM stdin;
    public          postgres    false    218   ,       �          0    16399    film 
   TABLE DATA           �   COPY public.film (name_film, id_film, rating, year, director, styles, poster, annotation, styles_array, wallpaper, number_of_ratings) FROM stdin;
    public          postgres    false    215   �       �          0    16502    film_comment 
   TABLE DATA           ;   COPY public.film_comment (id_comment, id_film) FROM stdin;
    public          postgres    false    219   �7       �          0    24616    user_rating 
   TABLE DATA           F   COPY public.user_rating (user_name, id_film, user_rating) FROM stdin;
    public          postgres    false    220   �7       �          0    16406    users 
   TABLE DATA           M   COPY public.users (avatar, user_name, email, passwd, collection) FROM stdin;
    public          postgres    false    216   e8       �           0    0    comment_id_comment_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.comment_id_comment_seq', 17, true);
          public          postgres    false    217            0           2606    16469    comment comment_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.comment
    ADD CONSTRAINT comment_pkey PRIMARY KEY (id_comment);
 >   ALTER TABLE ONLY public.comment DROP CONSTRAINT comment_pkey;
       public            postgres    false    218            2           2606    16516    film_comment film_comment_pkey 
   CONSTRAINT     d   ALTER TABLE ONLY public.film_comment
    ADD CONSTRAINT film_comment_pkey PRIMARY KEY (id_comment);
 H   ALTER TABLE ONLY public.film_comment DROP CONSTRAINT film_comment_pkey;
       public            postgres    false    219            ,           2606    16449    film film_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY public.film
    ADD CONSTRAINT film_pkey PRIMARY KEY (id_film);
 8   ALTER TABLE ONLY public.film DROP CONSTRAINT film_pkey;
       public            postgres    false    215            .           2606    16425    users users_pkey 
   CONSTRAINT     U   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_name);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    216            4           2606    16510    film_comment key_comment    FK CONSTRAINT     �   ALTER TABLE ONLY public.film_comment
    ADD CONSTRAINT key_comment FOREIGN KEY (id_comment) REFERENCES public.comment(id_comment);
 B   ALTER TABLE ONLY public.film_comment DROP CONSTRAINT key_comment;
       public          postgres    false    4656    218    219            5           2606    16517    film_comment key_film    FK CONSTRAINT     �   ALTER TABLE ONLY public.film_comment
    ADD CONSTRAINT key_film FOREIGN KEY (id_film) REFERENCES public.film(id_film) NOT VALID;
 ?   ALTER TABLE ONLY public.film_comment DROP CONSTRAINT key_film;
       public          postgres    false    4652    215    219            3           2606    16496    comment key_user    FK CONSTRAINT        ALTER TABLE ONLY public.comment
    ADD CONSTRAINT key_user FOREIGN KEY ("user") REFERENCES public.users(user_name) NOT VALID;
 :   ALTER TABLE ONLY public.comment DROP CONSTRAINT key_user;
       public          postgres    false    218    4654    216            �   L  x��T�nA<�|��n,�&��@�8�8q�b��r�(v�� rP�-!P$DA�q�l��c�=D�̮Y�8D���tWUw�#�4%_wh�[��Dwi�O�N�w��tmMѹn�&2BE����!�QH��&C�j��)"��8]Bݴx\�J��¦��m��fJ��ƶW��i��Fh1�6��p98����r$C���b�T�Y�.�mM�T������L��5R��i5�.g�h����n���a�Jy�!� �T� �{��TS�w�rc05�G��Q6�;ڕYA9�L��-jE�,ɞ~�w�ӊ������(㩘ia8��CZ�ޛL&'�u�)�g\=P`�G�i�FuN��"}8'L��w�fa��M�3m�}?�ģ�S�{�Մ��9Ag�A��E��3�D␲��2��&��=�;_�{�R�:��ʙ�ī�<lQگV�bS�@l�b�DHѣ+�,�&���v8���Dm,l��b	 r��
�(��|o-����o	��M�K%��ں����Y��)�U>[��	��m�)��;��3{6P�����8�5�Ɔ5ʡ-��g���WnF�Vt�O�,X]?^���������4I�H��8���Mp����'~��^��8������
���E�i���G�i�#�\�U�7�̌ ��	�\�˾+,*��O���"���Nm{���;�ɉ�W�/�bK�,���PXw3b�`9�Ck�I��Y���dv^�x��x�V��i�亨x��Euw׫,B�IwC�:X"��nve�?�����"�O�H-�:�>'+�[��[��R�?��*�      �      x��[]ogv��~��5�R�)K�团�Un������j�2d�۠( �����;�ư�u��")�m)�#��1��aI�y�s�y���m�M�@�����|�s�(�ӽ"+E~=)�-��h��8M�^�Nw�1�Ow��⨶Z�\[�ti�V����Nw�4)d�bX�O�=y�y1��7t��[[��Z�_���c��I]ϋ����L?���O��n�o�,��M��|��E�ʧ;�Tw+RY��&�����:ď\{ ��RYS.7*&��t_�ɋ�"Od�T~�W&��|.�K�{yp��.�7��N#)��#ý]t�^;f��F�+ew"�9�d��.	�V�!��C��ve\�X�N=JV��gӇ*?Y/����X������kk�xo}�����n����'kk�������^��|�O�����kk�ion�m��㭛����w�|T[Y^(^�Xc�́^�#�ZBI��gW����Q��wF������s��\��R+���"W�(����U3�<�ze/Y^B9fQ����V��^�r���������I��T"&&�M��;��&�{NT��D7x$7��o�S�T�}�� �Q7��)��TN�/����b��q���Z�J����y��>��t���.c��k���4�k��`��,�K�~T?�w�Nk�idf��8.���x?5�4�q�,6Q����֝�Oڭ{x����F�^ks��[�[w��[�aV��� {�KW.7�./][QY� ��i 1���î�T�PE����L���z
ӛvL���#{n�:Sǁ���Rߤ�*x��Tr]�Dל�GѫZ��_6}l��%특wğuO�����]�C#c�lv����?�p��>��
��@O��l�'�|!�(�S��@�@�5�O;A��Z!կN`��WYʰ�n��B#�`��7�i"�$����==EJ}�c"C�	� Kҹp�q��%(�����z.�a�}�g���s�]�����[���v5�=#��F�o	�7���[>hò����kK�Ģ�^Ԣ_�_F�*|���o�m~�b-'u�w��xؚa�>�7�T��XjW�s�m5Y!�"���S\�S��M�L����K����ACuh9���z?�g	f�x{ыʚ;�Q]���x��حH������Qs�&k���GN�CZNdJ�^�i��#�Q���i!GFbZ�s<�J��3��i���2�!!?�f� �r~1'�wl?��\خa�WĀ{��t纂��Y�ȩv���Xx���֪A�T������ͳEӳV�",mL@�&�=�dh��TJ@t�`��O1����w�`II8ՀHwh�"�tG���\x�Ib��Uh�߃!#0eEa�e�RSճ
ź	��h>�<r]i��)�c���!�=B�R'WQf~GP _~�f=Ҙ���F��q���M-Q���a��Pw=8���d�!�d�ZW��v���Zǭ� �>nٙ/��F���BZ��0��-��	HN#0D:?�7��ZBU %]�qA��~о��n�����փ��V=��0jͥ�RcI���k���L��Z]��z&�г��L�g��f���;p�!��l`	u��Y,��/ud��Գ� I��xb��j��&�,<��&�A�� Lt9�p����������2�2���>c�S��	��=V�Q�7�Q��K�:̌��Y |i�""�k�H��U�" �`([glQ(Ǘ�-{9Z#�}k��=�4x�V:}���7.�A���~�ʄ%!RKt/3�����ds��5��u|Iq������Oά~2�|�yv-�Ǟ���Ɲ�yy��Pŷ��2�N�^����Jj�!���-,��S�u7>�l�O}�Ȧ��)ū�嚠vd�o��(�s����[��ܲ���[+�Ƹ!�P, ? {�z�jگj��1�:����=Ns�ь9 �0{!��Z�il�Xp��^�[���ȩ)w``:cO4VW�O�� �)X:��0r)3||KY0м�o+�둆I�j���������#�؆�,Wc?��xc|��R9P�&H��ܙ��Y�~`�Tdu��q�}�UW��Y����u��MY���Z�Z ��2_��(�!b�EpNk�mHv�'z��M^�'x�=5�]���z$�:�3z���D�]���v��ד�cx��[�ھ�I���W|��`��'���!�;��)��G�^���B����5�t[�zy��/L$�Aq��S���PZ
g��#	3�E9�q(�����c^�C���Ң[S	���(�M�1#�%�g"ыӰ���\�
NW�y}UX4��{���~Ӻ��uk�}�&�!)����5�|N�M}�VjKVQ���4�J׈���b�L��AK@�i�!n�T-
��{����PJ���;?㘕�8#��+u`��[v ���Ȑ,B�P����� r$׳�q�Rl4���ajd���
���wz�$��(~�h[�e�;�τb�7ֵ8^��N�ӶTZ0tɯ��� � ��lQ0�F���JfTQ�z����!���깮���1��Ȩ4x��yJ#L��S.Pɓ��3��C�z1��"l qJ�|��"�Ch�1N1#�V�Z�c@Ʌ�u��1�Z]��mi�{���G�/���a�)��u0�PV[��2l$c�'*���ߣ�%`_�N�+5.Fՙ5��H�ă}�n�RV|<}�����*���f b�XH룐	�wvܸ8L\Y�\��~�|���B󯛒�kˍk�W/��J2n6��,MYh-������g	7GQ"�����m��%G�'�Y<M��w�q�ӏu�C|�5�XӃIf`�S�ы�bB+��P�i��YH��/�+��'4�Ȱ�K���2�YX�e|8���i�K���XJ�q�uj�ٌ���_�-w�v"[/�$$����?�O)J�Ş)��EJ>2/9������[E��u,�ÄՕH�f��""-To�����wL�U�.��y='���b!��n�R;�{z�	R����;뭻��k���-�6
쳂�5/�']m^]]F�d_�������n�g���J(o!�4��Y|Z���[�Ȃ�oQMH9���)�DJZB1���4�nl��g�*]�f1I4��J[��a2D������A�\6��(��uNQڕ7f5|`�&��k�P_�'��C��J��}$9ͫ��IH����lg�G��A��Q��Zj�Ͱq����NyD�+����r�����V{�-���B�F3��Q�(\~�5/���w���j��e���m4�jG��bB*w;p6�k���ںzʹ��^��x�mR�vJ6��tI��JdQS�6���:Yg�Q�-��r�(��a��rO�j�����l}����Ur2db�k0�Bf�\�c1�5P�������︍545�[�Hڬ�Z���z�E�U��T�C��KO	Yh��>��?�Z��|M���dV�f��0V�3+�͂u8�p]G���H�W�L��%Kj�2A�9�-�AN8}Go䒑,{:�`{#$�統
3�鈑jL��{N7��D)Yl0�,N���'z� Չ�W2�o(��fG�9ƨ���``�k��H��F2�s����cp6q���c�0HNDٲ-��$EIƶ��m��T���d��,��x9�@�50�� q�8�><��	�.5�׮,/_�rMg~Vk
�!�L�5iW��y��bG�e:̢f�+�8���1�U��uc棈�F�I�y�"Z��rp�ah����N�]Qb%�o֣f6c˺��<�ў���)�xt�Ba��A6����8��Fq�� ��檗y�gSk���	�O C��� X���bg����[��ͭo���������F�uo�F����$����U:f�j	�k��U-5��^YY�b��"�SD
�Ź�9�s>�� ����z���	n�.���Ar�񴌵���XHq����C�P���#���B@c�^�4�΂
��d�c�G�ǰ
7�S%�2�yJ�┹ឱ��[��v �  �Zy��L���a����
��j]��''jN���M�H�_�n�oon}pK�����{�Я.��܋*�&չ�'\Z�fql��8�����\��eT` �}
�����tp�j�G��O�N���ҥ�+�.�
�����f�t��| ���l������C�F�̞��3��8�V�DbZb���&��>�i����{[ea!����e1K�C:!�3�� MT�;�!�;�g�74,��l��CK�lS`��H�Tg��_Y��M]��՝>���Wn�?���	x�b�A�x3Z�UI��!�G��pgO��fO��~F�YN�	���C̕p=!�@���c���&GDb�t#W�r�"��\e~"����⋄�f3)c��C�V�� �F���R�J���9b�;xv�������Ň6���=Vh�U�h��hmo��"�xyi�M�\�W���bI�@�d~m��\�#fy�����k\������ahR�*�<i�=	� I��NlL�m'*�u_'�y���u��J���[p��+��ȭ��9?$�d�(<f\v�ze�7
����8��Ϝ�T�C>��me� e�����n&fΉB�#ZS����a�^�05�J4�3��ڎc]̟`:>�͑&��	���wE�~�\�3�jg�f�i����u��D��ձX���h�+��"�X�������KAlReJ��Y��T����Ы5�:�,F`k��+�������g�X���8F�q���ln�lW��ey�h�_���a0�hn)/��ut�����Hy�(Q�F9��r��NBnp��#��B#�<��(""C��V�'�}ZŇ�����h�y�~YF�X����X/cmsg�H=P�I<�	�U�DO�6����˔�w��lo򵚥�D�D�������Ҫ�6��犝<X��S�<<g0��ض8�?�98͍�V�i�؊xl j���qF�MT  '!F��/*��d��|�B�w���u 	�����Z'3��&��d)�v��+B�M�L3,m��O�����˪{ɤ��8g�[�9ipb�Rhq��쉕(�h����G�d���i�1w�}����,8a��������P�f�j��j�X�CǛ
I�{X������f(D�~����rY� �����5����y~���gF��i������i@i�j�M��s#X�,c�'�YyK��� ҥgA��0�������Z������E=)��?ūF��
ȗ�����p�0���bt�p�$aw.j%P/��j�6����ʗ[g�ăF׆R:6k�?���a�i���g���՗�2��1�F#�!.�fKu��3��|����\}�[��C�u�G����a�r�8��J;�ybO���J��������`��G�#K"��7���_�/�8_k�h�xiu�)?���u*���b����ڔ�_����%{�y�q���rH7165�'շ2����{.�"Y���&1f����4)�F� �F&Z��{�,a �Nw�=\��1������I�E��(��7hK�,��{#&�g�6>�7c������w��U̢23���N�F�h��=����$�ռ'=6��)���z��*G��x��a�c� ^��ڸӺ����V��#�t�W���tm�Jsoo5kŏL9o�,?�7֢�Z��u�O�%�?F3J����n���%�V�µ|I1�8~����\��ie��qB��g��~�]�Sy��ш���g�A���ʷ	�m�,�G≴hY �y��k[ΠQo��ؕ��X1��?R?*�ŷ8�wmN��u|�/�:�+��)
�y'��޽����֍HJ~��ߡg�a�=���^�<����]ЮĠJ�Z!����'� {Ѡ �K�B�k�j�
��aߐ����,���qҼ|�"/5h�?],z��3�Y�y�\f
��*����h�u�0>�x������zv����sY�賐���.3�I�Z��R�tѠ��f��������OX_YXk,,,���Ծ      �      x�3�4�2bcNc.NC�=... ��      �   �   x���IM�4�4��1,9M��!LCNc=�����NNc�Ĝ�
s ���7�4Bb#���FH�́��J��*�RA�pAj/L���b�Ŧ�=���@yd��Bd�9�U�Џ�ƃ����� �e����sU��qqq &�]�      �   �  x�͔M��0����街����Rh���=��@Y��Ķ[�$�߫�&��� _f��jx�a�Ւ���-���zh��.Z����@d�Q"I#�Ed*�(#e�5G@�*�2���U,��]�he���^o���������Ѹ�t�Mt>Pe�q��J!0�R+F����Ւ4iM��A}kpMR	w��1�a������:��.��?b���"�jn�\5�>�L��|=,�7e�Z���(p�����3�Kj��1��F�B��Ө����Œ~���Ricݨ��jK�Sٯ!����8[��x��+��s���6�c�����85/��y����S���y�L�3�O��׷C���.l\�6M"���x������x�{W�ˍ��{���2�S����u���C �qx�CȌ7 E�K�b�f+)Y����S�(�R\xm�1��F�b�0]���WY�F�J�c��ŪFh��`��dQk�tY�2Ƈ�l��F�     