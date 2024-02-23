-module(szekvbead).
-export([testing/0, toBitString/1, charToBitString/1, bitStringToChar/1, xOr/2, encrypt/2, stringToBitString/1, keygen/2, getKey/2]).
-export([stringFromBitString/1]).
-import(io, [fwrite/1, fwrite/2]).
-import(lists, [reverse/1]).

%XOR kódolás
%A feladat a XOR kódolás megvalósítása: https://en.wikipedia.org/wiki/XOR_cipher
%Segítségül megadom a feladat egy részletes bontását. Minden függvényt külön értékelek majd ki, a pontszámokat zárójelben megadtam a feladatoknál. Ha jól számoltam összesen 22+3 pont szerezhető.
%Azonban, ha nem szeretnéd ezt a megoldásmenetet követni, akkor saját megoldásmenettel is dolgozhatsz. Ebben az esetben is 22/25 pontot ér a megoldás, ha megvan a decodemessage. Amit szeretnék kérni, az az encode és decodemessage függvények interfésze kb egyezzen meg a kiírásban lévővel. Nyugodtan használhatsz a saját implementációdban mapet, binaryt vagy egyéb könyvtári adatszerkezetet is.
%A kódolás két paraméterrel rendelkezik: a kódolandó szöveg (Text) és a kulcs (Key). Az alábbi lépéseket kell majd végrehajtani:
%1. A Text bitstring reprezentációjának az előállítása.
%2. A Key ismétlése addig, amíg a kódolandó szöveg hosszát el nem érjük.
%3. Az ismételt kulcs bitstring reprezentációjának előállítása.
%4. XOR művelet végrehajtása a bitsrtingeken.
%Az implementációban a bitsringeket listával reprezentáljuk majd, azaz nullák és egyesek listájával. Ha egy karakternek megfelelő ($o) bitstringet meg akarjuk határozni, akkor az őt reprezentáló decimális számot (111) alakítjuk át kettes számrendszerbe  [0,1,1,0,1,1,1,1].

-type bitString() :: list(0|1).

testing() ->
  test1(),
  test2(),
  test3(),
  test4(),
  test5(),
  test6(),
  test7(),
  test8().

%Implementáld az alábbi függvényeket:

%1. Converting a decimal number to a bitstring (2 pont):
-spec toBitString(N::number())->Res::bitString().

toBitString(0) -> [];
toBitString(N) -> reverse(toBitString(N, [])).
toBitString(0, Acc) -> Acc;
toBitString(N, Acc) -> toBitString(N div 2, [N rem 2 | Acc]).

test1() ->
  fwrite("~s", ["Test1:"]),
  fwrite("~n~s", ["  toBitString(0) == []: "]),
  fwrite(toBitString(0) == []),
  fwrite("~n~s", ["  toBitString(2) == [0,1]: "]),
  fwrite(toBitString(2) == [0,1]),
  fwrite("~n~s", ["  toBitString(10) == [0,1,0,1]: "]),
  fwrite(toBitString(10) == [0,1,0,1]),
  fwrite("~n~s", ["  toBitString(12321) == [1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1]: "]),
  fwrite(toBitString(12321) == [1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1]),
  fwrite("~n~n", []).

%2. Converting a character to a bitstring (2 pont):
-spec charToBitString(A::char())->Res::bitString().
charToBitString(C) -> toBitString(C) ++ [0].

test2() ->
 fwrite("~s", ["Test2:"]),
 fwrite("~n~s", ["  charToBitString($a) == [1, 0, 0, 0, 0, 1, 1, 0]: "]),
 fwrite(charToBitString($a) == [1, 0, 0, 0, 0, 1, 1, 0]),
 fwrite("~n~s", ["  charToBitString($H) == [0, 0, 0, 1, 0, 0, 1, 0]: "]),
 fwrite(charToBitString($H) == [0, 0, 0, 1, 0, 0, 1, 0]),
 fwrite("~n~n", []).

%3. Converting a bitstring to a character (2 pont):
-spec bitStringToChar(A::bitString())->Res::char().
bitStringToChar(L) -> bitStringToChar(reverse(L), 0).

bitStringToChar([], N) -> N;
bitStringToChar([H|T], N) -> bitStringToChar(T, N*2 + H).

test3() ->
  fwrite("~s", ["Test3:"]),
  fwrite("~n~s", ["  bitStringToChar([1,0,0,0,1,0,1]) == $Q: "]),
  fwrite(bitStringToChar([1,0,0,0,1,0,1]) == $Q),
  fwrite("~n~s", ["  bitStringToChar([1,0,0,0,1,1]) == $1: "]),
  fwrite(bitStringToChar([1,0,0,0,1,1]) == $1),
  fwrite("~n~n", []).

%4. xor operation on bitstrings (2 pont):
-spec xOr(A::bitString(), B::bitString()) -> Res::bitString().
xOr(A, B) -> xOr(A, B, []).
xOr([], [], Acc) -> reverse(Acc);
xOr([H1|T1], [], Acc) -> xOr(T1, [], [H1|Acc]);
xOr([], [H2|T2], Acc) -> xOr([], T2, [H2|Acc]);
xOr([H1|T1], [H2|T2], Acc) -> xOr(T1, T2, [H1 bxor H2 | Acc]).

test4() ->
  fwrite("~s~n", ["Test4:"]),
  fwrite("  xOr([], [1,1,0,1,0,1]) == [1, 1, 0, 1, 0, 1]: ~p~n", [xOr([], [1,1,0,1,0,1]) == [1, 1, 0, 1, 0, 1]]),
  fwrite("  xOr([1,1,0,1,0,1], [1,0,1]) == [0, 1, 1, 1, 0, 1]: ~p~n", [xOr([1,1,0,1,0,1], [1,0,1]) == [0, 1, 1, 1, 0, 1]]),
  fwrite("~n~n", []).

%5. Encryption (3 pont).
%1. A Text bitstring reprezentációjának az előállítása.
%2. A Key ismétlése addig, amíg a kódolandó szöveg hosszát el nem érjük.
%3. Az ismételt kulcs bitstring reprezentációjának előállítása.
%4. XOR művelet végrehajtása a bitsrtingeken.

-spec encrypt(Text::string(), Key::string()) -> Chioer::string().
encrypt(Text, Key) -> 
  TextBitString = stringToBitString(Text),
  KeyBitString = stringToBitString(keygen(Text, Key)),
  stringFromBitString(encrypt(TextBitString, KeyBitString, [])).
encrypt([], [], Acc) -> reverse(Acc);
encrypt([H1|T1], [H2|T2], Acc) -> encrypt(T1, T2, [xOr(H1, H2) | Acc]).

-spec stringToBitString(S::string()) -> Res::[bitString()].
stringToBitString(S) -> stringToBitString(S, []).
stringToBitString([], Acc) -> reverse(Acc);
stringToBitString([H|T], Acc) -> stringToBitString(T, [charToBitString(H) | Acc]).

-spec stringFromBitString(L::[bitString()]) -> Res::string().
stringFromBitString(L) -> stringFromBitString(L, []).
stringFromBitString([], Acc) -> reverse(Acc);
stringFromBitString([H|T], Acc) -> stringFromBitString(T, [bitStringToChar(H) | Acc]).

-spec keygen(Text::string(), Key::string()) -> Key::string().
keygen(Text, Key) -> 
  Len = length(Text),
  LenKey = length(Key),
  if LenKey > Len -> keygen(Text, lists:sublist(Key, Len));
     LenKey < Len -> keygen(Text, Key ++ Key);
     true -> Key
  end.  

test5() ->
  fwrite("~s~n", ["Test5:"]),
  fwrite("  encrypt(\"Save Our Souls!\", \"SOS\") == [0,46,37,54,111,28,38,61,115,0,32,38,63,60,114]: ~p~n", [encrypt("Save Our Souls!", "SOS") ==  [0,46,37,54,111,28,38,61,115,0,32,38,63,60,114]]),
  fwrite("~n~n", []).

%6. Decryption (1 pont):

-spec decrypt(Cipher::string(), Key::string()) -> Text::string().
decrypt(Cipher, Key) -> 
  CipherBitString = stringToBitString(Cipher),
  KeyBitString = stringToBitString(keygen(Cipher, Key)),
  stringFromBitString(decrypt(CipherBitString, KeyBitString, [])).
decrypt([], [], Acc) -> reverse(Acc);
decrypt([H1|T1], [H2|T2], Acc) -> decrypt(T1, T2, [xOr(H1, H2) | Acc]).
%Mivel a xor művelet szimmetrikus, ezért a dekódolás könnyen megadható a kódoló függvényünk által a kulcs ismeretében.
test6() ->
  fwrite("~s~n", ["Test6:"]),
  fwrite("  decrypt(encrypt(\"Save Our Souls!\", \"SOS\"),\"SOS\") == \"Save Our Souls!\": ~p~n", [decrypt(encrypt("Save Our Souls!", "SOS"), "SOS") == "Save Our Souls!"]),
  fwrite("~n~n", []).

%7. Repeated text (2 pont):
%Állítsuk elő a függvényt, mely eldönti, hogy az A string ismételt elfordulása-e a B szöveg.
-spec isCycledIn(A::string(), B::string()) -> true | false.
isCycledIn(A, B) -> 
  LenA = length(A),
  LenB = length(B),
  if LenA > LenB -> false;
     LenA < LenB -> isCycledIn(A, lists:sublist(B, LenA));
     true -> A == B
  end.

test7() ->
  fwrite("~s~n", ["Test7:"]),
  fwrite("  isCycledIn(\"ab\", \"\") == false: ~p~n", [isCycledIn("ab", "") == false]),
  fwrite("  isCycledIn(\"ab\", \"cdddd\") == false: ~p~n", [isCycledIn("ab", "cdddd") == false]),
  fwrite("  isCycledIn(\"ab\", \"abb\") == false: ~p~n", [isCycledIn("ab", "abb") == false]),
  fwrite("  isCycledIn(\"ab\", \"abab\") == true: ~p~n", [isCycledIn("ab", "abab") == true]),
  fwrite("  isCycledIn(\"ab\", \"ababa\") == true: ~p~n", [isCycledIn("ab", "ababa") == true]),
  fwrite("~n~n", []).

%8. Determining the Key (4 pont):
%Amennyiben ismerjük az eredeti szöveget és a kódolt szöveget is, a kódolás kulcsa könnyen meghatározható:
  %- alkalmazzuk a dekódoló függvényt, hogy előállítsuk a kulcs ismétlődő sorozatát,
  %- keressük meg a legrövidebb ismétlődő részletet az eredmény stringben
-spec getKey(Text::string(), Cipher::string()) -> Key::string().
%- alkalmazzuk a dekódoló függvényt, hogy előállítsuk a kulcs ismétlődő sorozatát
%- keressük meg a legrövidebb ismétlődő részletet az eredmény stringben (isCycledIn függvény)
getKey(Text, Cipher) -> 
  LenText = length(Text),
  LenCipher = length(Cipher),
  if LenText > LenCipher -> getKey(Text, lists:sublist(Cipher, LenText));
     LenText < LenCipher -> getKey(Text ++ Text, Cipher);
     true -> getKey(Text, Cipher, [])
  end.
getKey(Text, Cipher, Acc) ->
  if isCycledIn(Text, Cipher) -> Text;
     true -> getKey(lists:sublist(Text, length(Text) - 1), Cipher, [lists:last(Text) | Acc])
  end.
  
test8() ->
  fwrite("~s~n", ["Test8:"]),
  fwrite("  getKey(\"Save Our Souls!\", encrypt(\"Save Our Souls!\", \"SOS\")) = \"SOS\": ~p~n", [getKey("Save Our Souls!", encrypt("Save Our Souls!", "SOS")) == "SOS"]), 
  fwrite("~n~n", []).