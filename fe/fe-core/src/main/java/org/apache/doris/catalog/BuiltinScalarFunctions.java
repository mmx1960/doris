// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package org.apache.doris.catalog;

import org.apache.doris.nereids.trees.expressions.functions.scalar.Abs;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Acos;
import org.apache.doris.nereids.trees.expressions.functions.scalar.AesDecrypt;
import org.apache.doris.nereids.trees.expressions.functions.scalar.AesEncrypt;
import org.apache.doris.nereids.trees.expressions.functions.scalar.AppendTrailingCharIfAbsent;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Ascii;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Asin;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Atan;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Bin;
import org.apache.doris.nereids.trees.expressions.functions.scalar.BitLength;
import org.apache.doris.nereids.trees.expressions.functions.scalar.BitmapAnd;
import org.apache.doris.nereids.trees.expressions.functions.scalar.BitmapAndCount;
import org.apache.doris.nereids.trees.expressions.functions.scalar.BitmapAndNot;
import org.apache.doris.nereids.trees.expressions.functions.scalar.BitmapAndNotCount;
import org.apache.doris.nereids.trees.expressions.functions.scalar.BitmapContains;
import org.apache.doris.nereids.trees.expressions.functions.scalar.BitmapCount;
import org.apache.doris.nereids.trees.expressions.functions.scalar.BitmapEmpty;
import org.apache.doris.nereids.trees.expressions.functions.scalar.BitmapFromString;
import org.apache.doris.nereids.trees.expressions.functions.scalar.BitmapHasAll;
import org.apache.doris.nereids.trees.expressions.functions.scalar.BitmapHasAny;
import org.apache.doris.nereids.trees.expressions.functions.scalar.BitmapHash;
import org.apache.doris.nereids.trees.expressions.functions.scalar.BitmapHash64;
import org.apache.doris.nereids.trees.expressions.functions.scalar.BitmapMax;
import org.apache.doris.nereids.trees.expressions.functions.scalar.BitmapMin;
import org.apache.doris.nereids.trees.expressions.functions.scalar.BitmapNot;
import org.apache.doris.nereids.trees.expressions.functions.scalar.BitmapOr;
import org.apache.doris.nereids.trees.expressions.functions.scalar.BitmapOrCount;
import org.apache.doris.nereids.trees.expressions.functions.scalar.BitmapSubsetInRange;
import org.apache.doris.nereids.trees.expressions.functions.scalar.BitmapSubsetLimit;
import org.apache.doris.nereids.trees.expressions.functions.scalar.BitmapToString;
import org.apache.doris.nereids.trees.expressions.functions.scalar.BitmapXor;
import org.apache.doris.nereids.trees.expressions.functions.scalar.BitmapXorCount;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Cbrt;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Ceil;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Ceiling;
import org.apache.doris.nereids.trees.expressions.functions.scalar.CharLength;
import org.apache.doris.nereids.trees.expressions.functions.scalar.CharacterLength;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Coalesce;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Concat;
import org.apache.doris.nereids.trees.expressions.functions.scalar.ConcatWs;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Conv;
import org.apache.doris.nereids.trees.expressions.functions.scalar.ConvertTz;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Cos;
import org.apache.doris.nereids.trees.expressions.functions.scalar.CurrentDate;
import org.apache.doris.nereids.trees.expressions.functions.scalar.CurrentTime;
import org.apache.doris.nereids.trees.expressions.functions.scalar.CurrentTimestamp;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Curtime;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Date;
import org.apache.doris.nereids.trees.expressions.functions.scalar.DateDiff;
import org.apache.doris.nereids.trees.expressions.functions.scalar.DateFormat;
import org.apache.doris.nereids.trees.expressions.functions.scalar.DateTrunc;
import org.apache.doris.nereids.trees.expressions.functions.scalar.DateV2;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Day;
import org.apache.doris.nereids.trees.expressions.functions.scalar.DayCeil;
import org.apache.doris.nereids.trees.expressions.functions.scalar.DayFloor;
import org.apache.doris.nereids.trees.expressions.functions.scalar.DayName;
import org.apache.doris.nereids.trees.expressions.functions.scalar.DayOfMonth;
import org.apache.doris.nereids.trees.expressions.functions.scalar.DayOfWeek;
import org.apache.doris.nereids.trees.expressions.functions.scalar.DayOfYear;
import org.apache.doris.nereids.trees.expressions.functions.scalar.DaysDiff;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Dceil;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Degrees;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Dexp;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Dfloor;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Dlog1;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Dlog10;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Dpow;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Dround;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Dsqrt;
import org.apache.doris.nereids.trees.expressions.functions.scalar.E;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Elt;
import org.apache.doris.nereids.trees.expressions.functions.scalar.EndsWith;
import org.apache.doris.nereids.trees.expressions.functions.scalar.EsQuery;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Exp;
import org.apache.doris.nereids.trees.expressions.functions.scalar.ExtractUrlParameter;
import org.apache.doris.nereids.trees.expressions.functions.scalar.FindInSet;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Floor;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Fmod;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Fpow;
import org.apache.doris.nereids.trees.expressions.functions.scalar.FromBase64;
import org.apache.doris.nereids.trees.expressions.functions.scalar.FromDays;
import org.apache.doris.nereids.trees.expressions.functions.scalar.FromUnixtime;
import org.apache.doris.nereids.trees.expressions.functions.scalar.GetJsonDouble;
import org.apache.doris.nereids.trees.expressions.functions.scalar.GetJsonInt;
import org.apache.doris.nereids.trees.expressions.functions.scalar.GetJsonString;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Greatest;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Hex;
import org.apache.doris.nereids.trees.expressions.functions.scalar.HllCardinality;
import org.apache.doris.nereids.trees.expressions.functions.scalar.HllEmpty;
import org.apache.doris.nereids.trees.expressions.functions.scalar.HllHash;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Hour;
import org.apache.doris.nereids.trees.expressions.functions.scalar.HourCeil;
import org.apache.doris.nereids.trees.expressions.functions.scalar.HourFloor;
import org.apache.doris.nereids.trees.expressions.functions.scalar.HoursDiff;
import org.apache.doris.nereids.trees.expressions.functions.scalar.If;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Initcap;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Instr;
import org.apache.doris.nereids.trees.expressions.functions.scalar.JsonArray;
import org.apache.doris.nereids.trees.expressions.functions.scalar.JsonObject;
import org.apache.doris.nereids.trees.expressions.functions.scalar.JsonQuote;
import org.apache.doris.nereids.trees.expressions.functions.scalar.JsonbExistsPath;
import org.apache.doris.nereids.trees.expressions.functions.scalar.JsonbExtract;
import org.apache.doris.nereids.trees.expressions.functions.scalar.JsonbExtractBigint;
import org.apache.doris.nereids.trees.expressions.functions.scalar.JsonbExtractBool;
import org.apache.doris.nereids.trees.expressions.functions.scalar.JsonbExtractDouble;
import org.apache.doris.nereids.trees.expressions.functions.scalar.JsonbExtractInt;
import org.apache.doris.nereids.trees.expressions.functions.scalar.JsonbExtractIsnull;
import org.apache.doris.nereids.trees.expressions.functions.scalar.JsonbExtractString;
import org.apache.doris.nereids.trees.expressions.functions.scalar.JsonbParse;
import org.apache.doris.nereids.trees.expressions.functions.scalar.JsonbParseErrorToInvalid;
import org.apache.doris.nereids.trees.expressions.functions.scalar.JsonbParseErrorToNull;
import org.apache.doris.nereids.trees.expressions.functions.scalar.JsonbParseErrorToValue;
import org.apache.doris.nereids.trees.expressions.functions.scalar.JsonbParseNotnull;
import org.apache.doris.nereids.trees.expressions.functions.scalar.JsonbParseNotnullErrorToInvalid;
import org.apache.doris.nereids.trees.expressions.functions.scalar.JsonbParseNotnullErrorToValue;
import org.apache.doris.nereids.trees.expressions.functions.scalar.JsonbParseNullable;
import org.apache.doris.nereids.trees.expressions.functions.scalar.JsonbParseNullableErrorToInvalid;
import org.apache.doris.nereids.trees.expressions.functions.scalar.JsonbParseNullableErrorToNull;
import org.apache.doris.nereids.trees.expressions.functions.scalar.JsonbParseNullableErrorToValue;
import org.apache.doris.nereids.trees.expressions.functions.scalar.JsonbType;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Least;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Left;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Length;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Ln;
import org.apache.doris.nereids.trees.expressions.functions.scalar.LocalTime;
import org.apache.doris.nereids.trees.expressions.functions.scalar.LocalTimestamp;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Locate;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Log;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Log10;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Log2;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Lower;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Lpad;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Ltrim;
import org.apache.doris.nereids.trees.expressions.functions.scalar.MakeDate;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Md5;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Md5Sum;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Minute;
import org.apache.doris.nereids.trees.expressions.functions.scalar.MinuteCeil;
import org.apache.doris.nereids.trees.expressions.functions.scalar.MinuteFloor;
import org.apache.doris.nereids.trees.expressions.functions.scalar.MinutesDiff;
import org.apache.doris.nereids.trees.expressions.functions.scalar.MoneyFormat;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Month;
import org.apache.doris.nereids.trees.expressions.functions.scalar.MonthCeil;
import org.apache.doris.nereids.trees.expressions.functions.scalar.MonthFloor;
import org.apache.doris.nereids.trees.expressions.functions.scalar.MonthName;
import org.apache.doris.nereids.trees.expressions.functions.scalar.MonthsDiff;
import org.apache.doris.nereids.trees.expressions.functions.scalar.MurmurHash332;
import org.apache.doris.nereids.trees.expressions.functions.scalar.MurmurHash364;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Negative;
import org.apache.doris.nereids.trees.expressions.functions.scalar.NotNullOrEmpty;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Now;
import org.apache.doris.nereids.trees.expressions.functions.scalar.NullIf;
import org.apache.doris.nereids.trees.expressions.functions.scalar.NullOrEmpty;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Nvl;
import org.apache.doris.nereids.trees.expressions.functions.scalar.ParseUrl;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Pi;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Pmod;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Positive;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Pow;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Power;
import org.apache.doris.nereids.trees.expressions.functions.scalar.QuantilePercent;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Quarter;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Radians;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Random;
import org.apache.doris.nereids.trees.expressions.functions.scalar.RegexpExtract;
import org.apache.doris.nereids.trees.expressions.functions.scalar.RegexpReplace;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Repeat;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Replace;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Reverse;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Right;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Round;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Rpad;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Rtrim;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Second;
import org.apache.doris.nereids.trees.expressions.functions.scalar.SecondCeil;
import org.apache.doris.nereids.trees.expressions.functions.scalar.SecondFloor;
import org.apache.doris.nereids.trees.expressions.functions.scalar.SecondsDiff;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Sign;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Sin;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Sleep;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Sm3;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Sm3sum;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Sm4Decrypt;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Sm4Encrypt;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Space;
import org.apache.doris.nereids.trees.expressions.functions.scalar.SplitPart;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Sqrt;
import org.apache.doris.nereids.trees.expressions.functions.scalar.StAstext;
import org.apache.doris.nereids.trees.expressions.functions.scalar.StAswkt;
import org.apache.doris.nereids.trees.expressions.functions.scalar.StCircle;
import org.apache.doris.nereids.trees.expressions.functions.scalar.StContains;
import org.apache.doris.nereids.trees.expressions.functions.scalar.StDistanceSphere;
import org.apache.doris.nereids.trees.expressions.functions.scalar.StGeometryfromtext;
import org.apache.doris.nereids.trees.expressions.functions.scalar.StGeomfromtext;
import org.apache.doris.nereids.trees.expressions.functions.scalar.StLinefromtext;
import org.apache.doris.nereids.trees.expressions.functions.scalar.StLinestringfromtext;
import org.apache.doris.nereids.trees.expressions.functions.scalar.StPoint;
import org.apache.doris.nereids.trees.expressions.functions.scalar.StPolyfromtext;
import org.apache.doris.nereids.trees.expressions.functions.scalar.StPolygon;
import org.apache.doris.nereids.trees.expressions.functions.scalar.StPolygonfromtext;
import org.apache.doris.nereids.trees.expressions.functions.scalar.StX;
import org.apache.doris.nereids.trees.expressions.functions.scalar.StY;
import org.apache.doris.nereids.trees.expressions.functions.scalar.StartsWith;
import org.apache.doris.nereids.trees.expressions.functions.scalar.StrLeft;
import org.apache.doris.nereids.trees.expressions.functions.scalar.StrRight;
import org.apache.doris.nereids.trees.expressions.functions.scalar.StrToDate;
import org.apache.doris.nereids.trees.expressions.functions.scalar.SubBitmap;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Substring;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Tan;
import org.apache.doris.nereids.trees.expressions.functions.scalar.TimeDiff;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Timestamp;
import org.apache.doris.nereids.trees.expressions.functions.scalar.ToBase64;
import org.apache.doris.nereids.trees.expressions.functions.scalar.ToBitmap;
import org.apache.doris.nereids.trees.expressions.functions.scalar.ToBitmapWithCheck;
import org.apache.doris.nereids.trees.expressions.functions.scalar.ToDate;
import org.apache.doris.nereids.trees.expressions.functions.scalar.ToDateV2;
import org.apache.doris.nereids.trees.expressions.functions.scalar.ToDays;
import org.apache.doris.nereids.trees.expressions.functions.scalar.ToQuantileState;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Trim;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Truncate;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Unhex;
import org.apache.doris.nereids.trees.expressions.functions.scalar.UnixTimestamp;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Upper;
import org.apache.doris.nereids.trees.expressions.functions.scalar.UtcTimestamp;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Version;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Week;
import org.apache.doris.nereids.trees.expressions.functions.scalar.WeekCeil;
import org.apache.doris.nereids.trees.expressions.functions.scalar.WeekFloor;
import org.apache.doris.nereids.trees.expressions.functions.scalar.WeekOfYear;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Weekday;
import org.apache.doris.nereids.trees.expressions.functions.scalar.WeeksDiff;
import org.apache.doris.nereids.trees.expressions.functions.scalar.Year;
import org.apache.doris.nereids.trees.expressions.functions.scalar.YearCeil;
import org.apache.doris.nereids.trees.expressions.functions.scalar.YearFloor;
import org.apache.doris.nereids.trees.expressions.functions.scalar.YearWeek;
import org.apache.doris.nereids.trees.expressions.functions.scalar.YearsDiff;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Builtin scalar functions.
 *
 * Note: Please ensure that this class only has some lists and no procedural code.
 *       It helps to be clear and concise.
 */
public class BuiltinScalarFunctions implements FunctionHelper {
    public final List<ScalarFunc> scalarFunctions = ImmutableList.of(
            scalar(Abs.class, "abs"),
            scalar(Acos.class, "acos"),
            scalar(AesDecrypt.class, "aes_decrypt"),
            scalar(AesEncrypt.class, "aes_encrypt"),
            scalar(AppendTrailingCharIfAbsent.class, "append_trailing_char_if_absent"),
            scalar(Ascii.class, "ascii"),
            scalar(Asin.class, "asin"),
            scalar(Atan.class, "atan"),
            scalar(Bin.class, "bin"),
            scalar(BitLength.class, "bit_length"),
            scalar(BitmapAnd.class, "bitmap_and"),
            scalar(BitmapAndCount.class, "bitmap_and_count"),
            scalar(BitmapAndNot.class, "bitmap_and_not"),
            scalar(BitmapAndNotCount.class, "bitmap_and_not_count"),
            scalar(BitmapContains.class, "bitmap_contains"),
            scalar(BitmapCount.class, "bitmap_count"),
            scalar(BitmapEmpty.class, "bitmap_empty"),
            scalar(BitmapFromString.class, "bitmap_from_string"),
            scalar(BitmapHasAll.class, "bitmap_has_all"),
            scalar(BitmapHasAny.class, "bitmap_has_any"),
            scalar(BitmapHash.class, "bitmap_hash"),
            scalar(BitmapHash64.class, "bitmap_hash64"),
            scalar(BitmapMax.class, "bitmap_max"),
            scalar(BitmapMin.class, "bitmap_min"),
            scalar(BitmapNot.class, "bitmap_not"),
            scalar(BitmapOr.class, "bitmap_or"),
            scalar(BitmapOrCount.class, "bitmap_or_count"),
            scalar(BitmapSubsetInRange.class, "bitmap_subset_in_range"),
            scalar(BitmapSubsetLimit.class, "bitmap_subset_limit"),
            scalar(BitmapToString.class, "bitmap_to_string"),
            scalar(BitmapXor.class, "bitmap_xor"),
            scalar(BitmapXorCount.class, "bitmap_xor_count"),
            scalar(Cbrt.class, "cbrt"),
            scalar(Ceil.class, "ceil"),
            scalar(Ceiling.class, "ceiling"),
            scalar(CharLength.class, "char_length"),
            scalar(CharacterLength.class, "character_length"),
            scalar(Coalesce.class, "coalesce"),
            scalar(Concat.class, "concat"),
            scalar(ConcatWs.class, "concat_ws"),
            scalar(Conv.class, "conv"),
            scalar(ConvertTz.class, "convert_tz"),
            scalar(Cos.class, "cos"),
            scalar(CurrentDate.class, "curdate", "current_date"),
            scalar(CurrentTime.class, "current_time"),
            scalar(CurrentTimestamp.class, "current_timestamp"),
            scalar(Curtime.class, "curtime"),
            scalar(Date.class, "date"),
            scalar(DateDiff.class, "datediff"),
            scalar(DateFormat.class, "date_format"),
            scalar(DateTrunc.class, "date_trunc"),
            scalar(DateV2.class, "datev2"),
            scalar(Day.class, "day"),
            scalar(DayCeil.class, "day_ceil"),
            scalar(DayFloor.class, "day_floor"),
            scalar(DayName.class, "dayname"),
            scalar(DayOfMonth.class, "dayofmonth"),
            scalar(DayOfWeek.class, "dayofweek"),
            scalar(DayOfYear.class, "dayofyear"),
            scalar(DaysDiff.class, "days_diff"),
            scalar(Dceil.class, "dceil"),
            scalar(Degrees.class, "degrees"),
            scalar(Dexp.class, "dexp"),
            scalar(Dfloor.class, "dfloor"),
            scalar(Dlog1.class, "dlog1"),
            scalar(Dlog10.class, "dlog10"),
            scalar(Dpow.class, "dpow"),
            scalar(Dround.class, "dround"),
            scalar(Dsqrt.class, "dsqrt"),
            scalar(E.class, "e"),
            scalar(Elt.class, "elt"),
            scalar(EndsWith.class, "ends_with"),
            scalar(EsQuery.class, "esquery"),
            scalar(Exp.class, "exp"),
            scalar(ExtractUrlParameter.class, "extract_url_parameter"),
            scalar(FindInSet.class, "find_in_set"),
            scalar(Floor.class, "floor"),
            scalar(Fmod.class, "fmod"),
            scalar(Fpow.class, "fpow"),
            scalar(FromBase64.class, "from_base64"),
            scalar(FromDays.class, "from_days"),
            scalar(FromUnixtime.class, "from_unixtime"),
            scalar(GetJsonDouble.class, "get_json_double"),
            scalar(GetJsonInt.class, "get_json_int"),
            scalar(GetJsonString.class, "get_json_string"),
            scalar(Greatest.class, "greatest"),
            scalar(Hex.class, "hex"),
            scalar(HllCardinality.class, "hll_cardinality"),
            scalar(HllEmpty.class, "hll_empty"),
            scalar(HllHash.class, "hll_hash"),
            scalar(Hour.class, "hour"),
            scalar(HourCeil.class, "hour_ceil"),
            scalar(HourFloor.class, "hour_floor"),
            scalar(HoursDiff.class, "hours_diff"),
            scalar(If.class, "if"),
            scalar(Initcap.class, "initcap"),
            scalar(Instr.class, "instr"),
            scalar(JsonArray.class, "json_array"),
            scalar(JsonObject.class, "json_object"),
            scalar(JsonQuote.class, "json_quote"),
            scalar(JsonbExistsPath.class, "jsonb_exists_path"),
            scalar(JsonbExtract.class, "jsonb_extract"),
            scalar(JsonbExtractBigint.class, "jsonb_extract_bigint"),
            scalar(JsonbExtractBool.class, "jsonb_extract_bool"),
            scalar(JsonbExtractDouble.class, "jsonb_extract_double"),
            scalar(JsonbExtractInt.class, "jsonb_extract_int"),
            scalar(JsonbExtractIsnull.class, "jsonb_extract_isnull"),
            scalar(JsonbExtractString.class, "jsonb_extract_string"),
            scalar(JsonbParse.class, "jsonb_parse"),
            scalar(JsonbParseErrorToInvalid.class, "jsonb_parse_error_to_invalid"),
            scalar(JsonbParseErrorToNull.class, "jsonb_parse_error_to_null"),
            scalar(JsonbParseErrorToValue.class, "jsonb_parse_error_to_value"),
            scalar(JsonbParseNotnull.class, "jsonb_parse_notnull"),
            scalar(JsonbParseNotnullErrorToInvalid.class, "jsonb_parse_notnull_error_to_invalid"),
            scalar(JsonbParseNotnullErrorToValue.class, "jsonb_parse_notnull_error_to_value"),
            scalar(JsonbParseNullable.class, "jsonb_parse_nullable"),
            scalar(JsonbParseNullableErrorToInvalid.class, "jsonb_parse_nullable_error_to_invalid"),
            scalar(JsonbParseNullableErrorToNull.class, "jsonb_parse_nullable_error_to_null"),
            scalar(JsonbParseNullableErrorToValue.class, "jsonb_parse_nullable_error_to_value"),
            scalar(JsonbType.class, "jsonb_type"),
            scalar(Least.class, "least"),
            scalar(Left.class, "left"),
            scalar(Length.class, "length"),
            scalar(Ln.class, "ln"),
            scalar(LocalTime.class, "localtime"),
            scalar(LocalTimestamp.class, "localtimestamp"),
            scalar(Locate.class, "locate"),
            scalar(Log.class, "log"),
            scalar(Log10.class, "log10"),
            scalar(Log2.class, "log2"),
            scalar(Lower.class, "lcase", "lower"),
            scalar(Lpad.class, "lpad"),
            scalar(Ltrim.class, "ltrim"),
            scalar(MakeDate.class, "makedate"),
            scalar(Md5.class, "md5"),
            scalar(Md5Sum.class, "md5sum"),
            scalar(Minute.class, "minute"),
            scalar(MinuteCeil.class, "minute_ceil"),
            scalar(MinuteFloor.class, "minute_floor"),
            scalar(MinutesDiff.class, "minutes_diff"),
            scalar(MoneyFormat.class, "money_format"),
            scalar(Month.class, "month"),
            scalar(MonthCeil.class, "month_ceil"),
            scalar(MonthFloor.class, "month_floor"),
            scalar(MonthName.class, "monthname"),
            scalar(MonthsDiff.class, "months_diff"),
            scalar(MurmurHash332.class, "murmur_hash3_32"),
            scalar(MurmurHash364.class, "murmur_hash3_64"),
            scalar(Negative.class, "negative"),
            scalar(NotNullOrEmpty.class, "not_null_or_empty"),
            scalar(Now.class, "now"),
            scalar(NullIf.class, "nullif"),
            scalar(NullOrEmpty.class, "null_or_empty"),
            scalar(Nvl.class, "ifnull", "nvl"),
            scalar(ParseUrl.class, "parse_url"),
            scalar(Pi.class, "pi"),
            scalar(Pmod.class, "pmod"),
            scalar(Positive.class, "positive"),
            scalar(Pow.class, "pow"),
            scalar(Power.class, "power"),
            scalar(QuantilePercent.class, "quantile_percent"),
            scalar(Quarter.class, "quarter"),
            scalar(Radians.class, "radians"),
            scalar(Random.class, "rand", "random"),
            scalar(RegexpExtract.class, "regexp_extract"),
            scalar(RegexpReplace.class, "regexp_replace"),
            scalar(Repeat.class, "repeat"),
            scalar(Replace.class, "replace"),
            scalar(Reverse.class, "reverse"),
            scalar(Right.class, "right"),
            scalar(Round.class, "round"),
            scalar(Rpad.class, "rpad"),
            scalar(Rtrim.class, "rtrim"),
            scalar(Second.class, "second"),
            scalar(SecondCeil.class, "second_ceil"),
            scalar(SecondFloor.class, "second_floor"),
            scalar(SecondsDiff.class, "seconds_diff"),
            scalar(Sign.class, "sign"),
            scalar(Sin.class, "sin"),
            scalar(Sleep.class, "sleep"),
            scalar(Sm3.class, "sm3"),
            scalar(Sm3sum.class, "sm3sum"),
            scalar(Sm4Decrypt.class, "sm4_decrypt"),
            scalar(Sm4Encrypt.class, "sm4_encrypt"),
            scalar(Space.class, "space"),
            scalar(SplitPart.class, "split_part"),
            scalar(Sqrt.class, "sqrt"),
            scalar(StAstext.class, "st_astext"),
            scalar(StAswkt.class, "st_aswkt"),
            scalar(StCircle.class, "st_circle"),
            scalar(StContains.class, "st_contains"),
            scalar(StDistanceSphere.class, "st_distance_sphere"),
            scalar(StGeometryfromtext.class, "st_geometryfromtext"),
            scalar(StGeomfromtext.class, "st_geomfromtext"),
            scalar(StLinefromtext.class, "st_linefromtext"),
            scalar(StLinestringfromtext.class, "st_linestringfromtext"),
            scalar(StPoint.class, "st_point"),
            scalar(StPolyfromtext.class, "st_polyfromtext"),
            scalar(StPolygon.class, "st_polygon"),
            scalar(StPolygonfromtext.class, "st_polygonfromtext"),
            scalar(StX.class, "st_x"),
            scalar(StY.class, "st_y"),
            scalar(StartsWith.class, "starts_with"),
            scalar(StrLeft.class, "strleft"),
            scalar(StrRight.class, "strright"),
            scalar(StrToDate.class, "str_to_date"),
            scalar(SubBitmap.class, "sub_bitmap"),
            scalar(Substring.class, "substr", "substring"),
            scalar(Tan.class, "tan"),
            scalar(TimeDiff.class, "timediff"),
            scalar(Timestamp.class, "timestamp"),
            scalar(ToBase64.class, "to_base64"),
            scalar(ToBitmap.class, "to_bitmap"),
            scalar(ToBitmapWithCheck.class, "to_bitmap_with_check"),
            scalar(ToDate.class, "to_date"),
            scalar(ToDateV2.class, "to_datev2"),
            scalar(ToDays.class, "to_days"),
            scalar(ToQuantileState.class, "to_quantile_state"),
            scalar(Trim.class, "trim"),
            scalar(Truncate.class, "truncate"),
            scalar(Unhex.class, "unhex"),
            scalar(UnixTimestamp.class, "unix_timestamp"),
            scalar(Upper.class, "ucase", "upper"),
            scalar(UtcTimestamp.class, "utc_timestamp"),
            scalar(Version.class, "version"),
            scalar(Week.class, "week"),
            scalar(WeekCeil.class, "week_ceil"),
            scalar(WeekFloor.class, "week_floor"),
            scalar(WeekOfYear.class, "weekofyear"),
            scalar(Weekday.class, "weekday"),
            scalar(WeeksDiff.class, "weeks_diff"),
            scalar(Year.class, "year"),
            scalar(YearCeil.class, "year_ceil"),
            scalar(YearFloor.class, "year_floor"),
            scalar(YearWeek.class, "yearweek"),
            scalar(YearsDiff.class, "years_diff")
    );

    public static final BuiltinScalarFunctions INSTANCE = new BuiltinScalarFunctions();

    // Note: Do not add any code here!
    private BuiltinScalarFunctions() {}
}
