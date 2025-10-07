INSERT INTO public.domains (id, name) VALUES (1, 'Sociaal');
INSERT INTO public.domains (id, name) VALUES (2, 'Economisch');
INSERT INTO public.domains (id, name) VALUES (5, 'Bestuur & Organisatie');

INSERT INTO public.dmns (id, name, owner, domain_id) VALUES (1, 'Toekenning Wmo-voorziening', 'Jan Klaassen', 1);
INSERT INTO public.dmns (id, name, owner, domain_id) VALUES (2, 'Jeugdzorg-indicatie', 'Piet Pieters', 1);
INSERT INTO public.dmns (id, name, owner, domain_id) VALUES (3, 'Inkomensondersteuning', 'Klaas Hermans', 1);
INSERT INTO public.dmns (id, name, owner, domain_id) VALUES (4, 'Participatieplicht', 'Herman Jans', 1);
INSERT INTO public.dmns (id, name, owner, domain_id) VALUES (5, 'Eigen bijdrage berekening', 'Jan Klaassen', 1);
INSERT INTO public.dmns (id, name, owner, domain_id) VALUES (6, 'Vergunning evenementen', 'Jan Klaassen', 2);
INSERT INTO public.dmns (id, name, owner, domain_id) VALUES (7, 'Subsidietoekenning bedrijven', 'Jan Klaassen', 2);
INSERT INTO public.dmns (id, name, owner, domain_id) VALUES (8, 'Standplaatsvergunning', 'Klaas Hermans', 2);
INSERT INTO public.dmns (id, name, owner, domain_id) VALUES (9, 'Toewijzing bedrijventerrein', 'Piet Pieters', 2);
INSERT INTO public.dmns (id, name, owner, domain_id) VALUES (10, 'Precariobelasting vrijstelling', 'Klaas Hermans', 2);
INSERT INTO public.dmns (id, name, owner, domain_id) VALUES (11, 'Bezwaarbeoordeling', 'Jan Klaassen', 5);
INSERT INTO public.dmns (id, name, owner, domain_id) VALUES (12, 'Inspraakrecht', 'Klaas Hermans', 5);
INSERT INTO public.dmns (id, name, owner, domain_id) VALUES (13, 'Subsidiebeoordeling verenigingen', 'Klaas Hermans', 5);
INSERT INTO public.dmns (id, name, owner, domain_id) VALUES (14, 'Vergaderquorum', 'Piet Pieters', 5);
INSERT INTO public.dmns (id, name, owner, domain_id) VALUES (15, 'Integriteitscheck', 'Herman Jans', 5);
INSERT INTO public.dmns (id, name, owner, domain_id) VALUES (16, 'Beslissen IIT', 'Mark Akkermans', 1);

INSERT INTO public.versions (dmn_id, version, status, file_blob, modified_by, modified_date, created_by, created_date) VALUES (16, 1, 1, convert_to('<?xml version="1.0" encoding="UTF-8"?>
<definitions xmlns="https://www.omg.org/spec/DMN/20191111/MODEL/" xmlns:dmndi="https://www.omg.org/spec/DMN/20191111/DMNDI/" xmlns:dc="http://www.omg.org/spec/DMN/20180521/DC/" xmlns:di="http://www.omg.org/spec/DMN/20180521/DI/" xmlns:biodi="http://bpmn.io/schema/dmn/biodi/2.0" id="Definitions_16k8lsw" name="DRD" namespace="http://camunda.org/schema/1.0/dmn" exporter="Camunda Modeler" exporterVersion="4.9.0">
  <decision id="Decision_07xyq1v" name="IIT (art 36)">
    <informationRequirement id="InformationRequirement_1twr4qf">
      <requiredDecision href="#GBI-BR-001" />
    </informationRequirement>
    <informationRequirement id="InformationRequirement_1xqept6">
      <requiredDecision href="#Decision_1abq2rm" />
    </informationRequirement>
    <informationRequirement id="InformationRequirement_19r1o08">
      <requiredDecision href="#Decision_1kggvgs" />
    </informationRequirement>
    <informationRequirement id="InformationRequirement_1k28dub">
      <requiredDecision href="#Decision_1ac2lm5" />
    </informationRequirement>
    <informationRequirement id="InformationRequirement_1xifqtc">
      <requiredDecision href="#Decision_1pqlsl4" />
    </informationRequirement>
    <informationRequirement id="InformationRequirement_1ie31bs">
      <requiredDecision href="#Decision_1g1bgbm" />
    </informationRequirement>
    <informationRequirement id="InformationRequirement_1g2rusm">
      <requiredDecision href="#Decision_0bbe0q4" />
    </informationRequirement>
    <informationRequirement id="InformationRequirement_0rc2sk1">
      <requiredDecision href="#Decision_04k6u1j" />
    </informationRequirement>
    <informationRequirement id="InformationRequirement_17un8ym">
      <requiredDecision href="#Decision_194g2m2" />
    </informationRequirement>
    <decisionTable id="DecisionTable_0dqvkbl" hitPolicy="COLLECT">
      <input id="InputClause_1c9rp5q" label="Woonplaats in gemeente" biodi:width="192">
        <inputExpression id="LiteralExpression_05lvtog" typeRef="boolean">
          <text></text>
        </inputExpression>
      </input>
      <input id="InputClause_0dgiajw" label="LeeftijdOK">
        <inputExpression id="LiteralExpression_0aj8m89" typeRef="boolean">
          <text></text>
        </inputExpression>
      </input>
      <input id="InputClause_0eo9r8a" label="Langdurig laag inkomen">
        <inputExpression id="LiteralExpression_1u48tem" typeRef="boolean">
          <text></text>
        </inputExpression>
      </input>
      <input id="InputClause_1rccugj" label="Vermogen boven grens">
        <inputExpression id="LiteralExpression_16qb3ra" typeRef="boolean">
          <text></text>
        </inputExpression>
      </input>
      <input id="InputClause_0uh3efw" label="Perspectief inkomensverbtering">
        <inputExpression id="LiteralExpression_1dar0yx" typeRef="boolean">
          <text>Perspectief</text>
        </inputExpression>
      </input>
      <input id="InputClause_1es9dtu" label="Omstandigheden">
        <inputExpression id="LiteralExpression_0vhwwrn" typeRef="boolean">
          <text>Omstandigheden</text>
        </inputExpression>
      </input>
      <input id="InputClause_0i76vo5" label="IIT afgelopen 12 maanden">
        <inputExpression id="LiteralExpression_066zwbj" typeRef="boolean">
          <text>IIT 12mnd</text>
        </inputExpression>
      </input>
      <input id="InputClause_0qrllh7" label="Geldige verblijfstitel">
        <inputExpression id="LiteralExpression_1hr5vjv" typeRef="boolean">
          <text>Verblijfstitel OK</text>
        </inputExpression>
      </input>
      <output id="Output_1" label="RechtopIIT" name="Recht op IIT" typeRef="boolean" biodi:width="192" />
      <output id="OutputClause_0omozk9" name="Reden afwijzing" typeRef="string" biodi:width="191" />
      <rule id="DecisionRule_1p7f6am">
        <inputEntry id="UnaryTests_0hj41hn">
          <text>false</text>
        </inputEntry>
        <inputEntry id="UnaryTests_0ts2rj6">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_1khrgk5">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_1po2ojs">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_18tah4n">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_1l3fcoo">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_06ahcyr">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_0hnhpd5">
          <text></text>
        </inputEntry>
        <outputEntry id="LiteralExpression_1o6msgg">
          <text>false</text>
        </outputEntry>
        <outputEntry id="LiteralExpression_1rjc8oj">
          <text>"01"</text>
        </outputEntry>
      </rule>
      <rule id="DecisionRule_1htxakn">
        <inputEntry id="UnaryTests_0ztopr7">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_094iyq1">
          <text>false</text>
        </inputEntry>
        <inputEntry id="UnaryTests_1xf0foj">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_1vkvk1c">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_0rtvo8r">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_087r7zo">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_1r7lg3h">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_1vqkrnc">
          <text></text>
        </inputEntry>
        <outputEntry id="LiteralExpression_19rt1x1">
          <text>false</text>
        </outputEntry>
        <outputEntry id="LiteralExpression_03df8dl">
          <text>"08"</text>
        </outputEntry>
      </rule>
      <rule id="DecisionRule_1xmlmil">
        <inputEntry id="UnaryTests_1qfllvy">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_0varbkj">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_0xssf2j">
          <text>false</text>
        </inputEntry>
        <inputEntry id="UnaryTests_0p8ez0g">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_1ax488q">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_1kibk0v">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_1bi4g3d">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_02e4d11">
          <text></text>
        </inputEntry>
        <outputEntry id="LiteralExpression_16th5lf">
          <text>false</text>
        </outputEntry>
        <outputEntry id="LiteralExpression_0860s0s">
          <text>"02"</text>
        </outputEntry>
      </rule>
      <rule id="DecisionRule_1yfcuyd">
        <inputEntry id="UnaryTests_1nqseof">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_0oimk3p">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_1ushab7">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_04bvqa9">
          <text>true</text>
        </inputEntry>
        <inputEntry id="UnaryTests_1fncdbt">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_0a9fw08">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_1u81vaj">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_0jn27eu">
          <text></text>
        </inputEntry>
        <outputEntry id="LiteralExpression_0u0qtiu">
          <text>false</text>
        </outputEntry>
        <outputEntry id="LiteralExpression_0cjtuln">
          <text>"03"</text>
        </outputEntry>
      </rule>
      <rule id="DecisionRule_1cdwkas">
        <inputEntry id="UnaryTests_0vvktsy">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_0th14gz">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_1qqel23">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_05gr2o8">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_0yp0tkj">
          <text>true</text>
        </inputEntry>
        <inputEntry id="UnaryTests_0uke03d">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_0bzshxk">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_12t7ujc">
          <text></text>
        </inputEntry>
        <outputEntry id="LiteralExpression_1luq4op">
          <text>false</text>
        </outputEntry>
        <outputEntry id="LiteralExpression_180xysa">
          <text>"04"</text>
        </outputEntry>
      </rule>
      <rule id="DecisionRule_0f311r2">
        <inputEntry id="UnaryTests_1uq3dhq">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_0su9n6v">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_1dl6nl9">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_0ozlvpw">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_11jb42s">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_178musx">
          <text>true</text>
        </inputEntry>
        <inputEntry id="UnaryTests_10wmemk">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_1dcyjbf">
          <text></text>
        </inputEntry>
        <outputEntry id="LiteralExpression_1nj7eyi">
          <text>false</text>
        </outputEntry>
        <outputEntry id="LiteralExpression_1hk3rb8">
          <text>"05"</text>
        </outputEntry>
      </rule>
      <rule id="DecisionRule_1brd2x9">
        <inputEntry id="UnaryTests_1yb8vwg">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_09qfy1n">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_0gw838j">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_1sjsdzx">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_1gv4g9r">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_0w665vm">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_1bke51s">
          <text>true</text>
        </inputEntry>
        <inputEntry id="UnaryTests_0uqbtox">
          <text></text>
        </inputEntry>
        <outputEntry id="LiteralExpression_02u42s5">
          <text>false</text>
        </outputEntry>
        <outputEntry id="LiteralExpression_1sjqdc1">
          <text>"06"</text>
        </outputEntry>
      </rule>
      <rule id="DecisionRule_1d8qw7u">
        <inputEntry id="UnaryTests_0yza51c">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_0goy0nb">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_0majnmp">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_1w0wt2q">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_19887mr">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_0vw16mi">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_03gg0zj">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_1xoe5lj">
          <text>false</text>
        </inputEntry>
        <outputEntry id="LiteralExpression_09vbchy">
          <text>false</text>
        </outputEntry>
        <outputEntry id="LiteralExpression_1xrd6qd">
          <text>"07"</text>
        </outputEntry>
      </rule>
      <rule id="DecisionRule_0ur76k2">
        <inputEntry id="UnaryTests_14qqx4x">
          <text>true</text>
        </inputEntry>
        <inputEntry id="UnaryTests_04rdo1f">
          <text>true</text>
        </inputEntry>
        <inputEntry id="UnaryTests_0z05xjj">
          <text>true</text>
        </inputEntry>
        <inputEntry id="UnaryTests_0zpo12q">
          <text>false</text>
        </inputEntry>
        <inputEntry id="UnaryTests_1k217m9">
          <text>false</text>
        </inputEntry>
        <inputEntry id="UnaryTests_02e7nns">
          <text>false</text>
        </inputEntry>
        <inputEntry id="UnaryTests_0l4d6lw">
          <text>false</text>
        </inputEntry>
        <inputEntry id="UnaryTests_0gf2na2">
          <text>true</text>
        </inputEntry>
        <outputEntry id="LiteralExpression_0lnzqcz">
          <text>true</text>
        </outputEntry>
        <outputEntry id="LiteralExpression_1it3lvv">
          <text></text>
        </outputEntry>
      </rule>
      <rule id="DecisionRule_1v1xsx6">
        <inputEntry id="UnaryTests_0urubk5">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_097yi2h">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_0hf0qyd">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_04d4ytn">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_0x9ira6">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_1bemqgu">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_0u9m1pd">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_0l20zuq">
          <text></text>
        </inputEntry>
        <outputEntry id="LiteralExpression_060hv2k">
          <text></text>
        </outputEntry>
        <outputEntry id="LiteralExpression_1fvp3lv">
          <text></text>
        </outputEntry>
      </rule>
    </decisionTable>
  </decision>
  <decision id="Decision_1kggvgs" name="Geen in aanmerking te nemen vermogen (art 36.1)">
    <informationRequirement id="InformationRequirement_02g71hk">
      <requiredDecision href="#Decision_1o8uc3d" />
    </informationRequirement>
  </decision>
  <decision id="Decision_1o8uc3d" name="Vermogen (art 34)">
    <informationRequirement id="InformationRequirement_1ufersf">
      <requiredDecision href="#Decision_0k9cyom" />
    </informationRequirement>
    <informationRequirement id="InformationRequirement_17dfd2s">
      <requiredDecision href="#Decision_02mzepc" />
    </informationRequirement>
  </decision>
  <decision id="Decision_1fpfrrv" name="Heeft de persoon al zijn krachten en bekwaamheden ingezet? (art 36.2a" />
  <decision id="Decision_1tjyffq" name="inspanningen (art 36.2b)" />
  <decision id="Decision_0bbe0q4" name="Eerder IIT verleend in 12 maanden (art 36.3)" />
  <decision id="Decision_04k6u1j" name="nvt artikelen (art 36.4)">
    <informationRequirement id="InformationRequirement_0fuqu0p">
      <requiredDecision href="#Decision_1y37ew1" />
    </informationRequirement>
    <informationRequirement id="InformationRequirement_0nh3f8q">
      <requiredDecision href="#Decision_006rxfz" />
    </informationRequirement>
    <informationRequirement id="InformationRequirement_1ktd6mg">
      <requiredDecision href="#Decision_09a41bd" />
    </informationRequirement>
    <informationRequirement id="InformationRequirement_1fpls5m">
      <requiredDecision href="#Decision_0l5dyow" />
    </informationRequirement>
  </decision>
  <decision id="Decision_1y37ew1" name="Onderhoudsplicht ouders (art 12)" />
  <decision id="Decision_006rxfz" name="Vaststelling op aanvraag (art 43)" />
  <decision id="Decision_09a41bd" name="Schuldenlast (art 49)" />
  <decision id="Decision_0l5dyow" name="Voorschot (art 52)" />
  <inputData id="InputData_15d86p9" name="Ref: Referteperiode" />
  <inputData id="InputData_0jmf6og" name="Ref: LaagInkomen" />
  <inputData id="InputData_0o0yzri" name="Leeftijd" />
  <inputData id="InputData_1vm5uj4" name="Ref: Pensioengerechtigd" />
  <decision id="Decision_0k9cyom" name="Vermogensgrens(art 34.3)">
    <informationRequirement id="InformationRequirement_02bh8dd">
      <requiredInput href="#InputData_149951b" />
    </informationRequirement>
    <informationRequirement id="InformationRequirement_0paevvg">
      <requiredDecision href="#Decision_0wtqmfc" />
    </informationRequirement>
  </decision>
  <inputData id="InputData_1virsau" name="Vermogenscomponenten" />
  <inputData id="InputData_149951b" name="Ref: bedrag vrijlating vermogen" />
  <decision id="Decision_1vafsvk" name="Vrijlatingen + regels per component">
    <informationRequirement id="InformationRequirement_1vmve45">
      <requiredInput href="#InputData_1virsau" />
    </informationRequirement>
  </decision>
  <decision id="Decision_02mzepc" name="Vastgesteld vermogen (art 34.1)">
    <informationRequirement id="InformationRequirement_0ber3g6">
      <requiredDecision href="#Decision_05ylk1d" />
    </informationRequirement>
    <informationRequirement id="InformationRequirement_13bdf5n">
      <requiredDecision href="#Decision_1vafsvk" />
    </informationRequirement>
  </decision>
  <decision id="Decision_0wtqmfc" name="Alleenstaande, alleenstaande ouder en gezin (art 4)" />
  <decision id="Decision_05ylk1d" name="NIet in vermogen (34.2)">
    <informationRequirement id="InformationRequirement_0qp1tda">
      <requiredDecision href="#Decision_19i2gri" />
    </informationRequirement>
  </decision>
  <decision id="Decision_19i2gri" name="Vermindering vermogen (art 34.4)" />
  <decision id="Decision_0jqfpdf" name="Vermogen in woning + erf (art 34.2d">
    <informationRequirement id="InformationRequirement_15xp265">
      <requiredInput href="#InputData_002pq02" />
    </informationRequirement>
    <informationRequirement id="InformationRequirement_0b6fsvh">
      <requiredInput href="#InputData_17b8ac9" />
    </informationRequirement>
  </decision>
  <inputData id="InputData_17b8ac9" name="Vermogen woning + erf" />
  <inputData id="InputData_002pq02" name="Ref: grensbedrag 34.2d" />
  <knowledgeSource id="KnowledgeSource_18u6e4t" name="Art 50">
    <authorityRequirement id="AuthorityRequirement_1n2z1zd">
      <requiredInput href="#InputData_17b8ac9" />
    </authorityRequirement>
  </knowledgeSource>
  <decision id="GBI-BR-001" name="Leeftijd OK (art 36.1)">
    <informationRequirement id="InformationRequirement_1k4lgbn">
      <requiredInput href="#InputData_0o0yzri" />
    </informationRequirement>
    <informationRequirement id="InformationRequirement_1vxg56b">
      <requiredInput href="#InputData_1vm5uj4" />
    </informationRequirement>
    <decisionTable id="DecisionTable_0h1krer" hitPolicy="FIRST">
      <input id="InputClause_0ot0skh" label="Leeftijd">
        <inputExpression id="LiteralExpression_0oiqi73" typeRef="string" />
      </input>
      <output id="OutputClause_02hlguj" name="LeeftijdOK" typeRef="boolean" biodi:width="190" />
      <rule id="DecisionRule_10yuiqz">
        <inputEntry id="UnaryTests_1bczno0">
          <text>&lt; 21</text>
        </inputEntry>
        <outputEntry id="LiteralExpression_1f6doc4">
          <text>false</text>
        </outputEntry>
      </rule>
      <rule id="DecisionRule_15l8ci3">
        <description>i.e. de datum waarop persoon recht heeft op AOW????</description>
        <inputEntry id="UnaryTests_1xefo0t">
          <text>&gt; Ref:Pensioengerechtigde leeftijd</text>
        </inputEntry>
        <outputEntry id="LiteralExpression_1qslyob">
          <text>false</text>
        </outputEntry>
      </rule>
      <rule id="DecisionRule_0c0z0z8">
        <inputEntry id="UnaryTests_1pz4am7">
          <text>-</text>
        </inputEntry>
        <outputEntry id="LiteralExpression_06pazxc">
          <text>true</text>
        </outputEntry>
      </rule>
    </decisionTable>
  </decision>
  <decision id="Decision_1ac2lm5" name="Geen uitzicht inkomensverbetering (art 36.1)">
    <decisionTable id="DecisionTable_0v740z6">
      <input id="InputClause_0axyj2f">
        <inputExpression id="LiteralExpression_0xkc5oz" typeRef="string" />
      </input>
      <output id="OutputClause_1rcv7jj" typeRef="string" />
      <rule id="DecisionRule_00x99rz">
        <inputEntry id="UnaryTests_022di99">
          <text></text>
        </inputEntry>
        <outputEntry id="LiteralExpression_1a4lz3y">
          <text></text>
        </outputEntry>
      </rule>
      <rule id="DecisionRule_0va7z4u">
        <inputEntry id="UnaryTests_0n1vctl">
          <text></text>
        </inputEntry>
        <outputEntry id="LiteralExpression_1mnn8ky">
          <text></text>
        </outputEntry>
      </rule>
    </decisionTable>
  </decision>
  <decision id="Decision_1pqlsl4" name="ZIjn er volgens het college omstandigheden van de persoon die aanspraak op IIT hadden kunnen vermijden? (art 36.1)">
    <informationRequirement id="InformationRequirement_15g8qce">
      <requiredDecision href="#Decision_1fpfrrv" />
    </informationRequirement>
    <informationRequirement id="InformationRequirement_1cu8t2z">
      <requiredDecision href="#Decision_1tjyffq" />
    </informationRequirement>
    <decisionTable id="DecisionTable_17kcm6k">
      <input id="InputClause_1moxklt" label="Krachten en bekwaamheden">
        <inputExpression id="LiteralExpression_0z0dyey" typeRef="string" />
      </input>
      <input id="InputClause_0hllby1" label="Inspanningen">
        <inputExpression id="LiteralExpression_06ibnym" typeRef="string">
          <text></text>
        </inputExpression>
      </input>
      <output id="OutputClause_1eywzhg" typeRef="string" />
      <rule id="DecisionRule_0id056t">
        <inputEntry id="UnaryTests_1fi1n2w">
          <text>Ja</text>
        </inputEntry>
        <inputEntry id="UnaryTests_1t5ih5o">
          <text></text>
        </inputEntry>
        <outputEntry id="LiteralExpression_16vdhwc">
          <text>Ja</text>
        </outputEntry>
      </rule>
      <rule id="DecisionRule_1dctfgh">
        <inputEntry id="UnaryTests_0oenebs">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_0g295hz">
          <text>Ja</text>
        </inputEntry>
        <outputEntry id="LiteralExpression_0cge4nx">
          <text>Ja</text>
        </outputEntry>
      </rule>
      <rule id="DecisionRule_160ufdx">
        <inputEntry id="UnaryTests_00w4k7b">
          <text>Nee</text>
        </inputEntry>
        <inputEntry id="UnaryTests_1tinlzw">
          <text>Nee</text>
        </inputEntry>
        <outputEntry id="LiteralExpression_07gnmf3">
          <text>Nee</text>
        </outputEntry>
      </rule>
    </decisionTable>
  </decision>
  <decision id="Decision_1g1bgbm" name="Verzoek (art 36.1)" />
  <decision id="Decision_194g2m2" name="WoonplaatsInGemeente">
    <decisionTable id="DecisionTable_1u6h7g8" hitPolicy="FIRST">
      <input id="InputClause_1m67xei" label="Woonplaats">
        <inputExpression id="LiteralExpression_1mt0tb6" typeRef="string" />
      </input>
      <output id="OutputClause_003fm9t" name="WoonplaatsInGemeente" typeRef="boolean" biodi:width="192" />
      <rule id="DecisionRule_0c86il5">
        <inputEntry id="UnaryTests_0ppgu39">
          <text>"Ref:Gemeente"</text>
        </inputEntry>
        <outputEntry id="LiteralExpression_1doo1xt">
          <text>true</text>
        </outputEntry>
      </rule>
    </decisionTable>
  </decision>
  <inputData id="InputData_042i8cf" name="Ref:TeveelMaandenHoog" />
  <inputData id="InputData_0sfd9vu" name="InkomenMaand" />
  <decision id="Decision_1abq2rm" name="Langdurig laag inkomen (art 36.1)">
    <informationRequirement id="InformationRequirement_1m3iame">
      <requiredDecision href="#Decision_021x8zv" />
    </informationRequirement>
    <informationRequirement id="InformationRequirement_0l6mumu">
      <requiredDecision href="#Decision_0ta8sug" />
    </informationRequirement>
    <decisionTable id="DecisionTable_094l3wb">
      <input id="InputClause_016zf5l" label="GemiddeldInkomenLaag">
        <inputExpression id="LiteralExpression_1rja332" typeRef="boolean" />
      </input>
      <input id="InputClause_1oz0a7w" label="TeveelMaandInkomenHoog">
        <inputExpression id="LiteralExpression_0mpp2w1" typeRef="boolean">
          <text></text>
        </inputExpression>
      </input>
      <output id="OutputClause_1t42j34" label="LangdurigLaagInkomen" name="LangdurigLaagInkomen" typeRef="boolean" />
      <rule id="DecisionRule_0bdf95k">
        <description>Gemiddeld maandinkomen in referteperiode is lager dan referentiebedrag, en aanal uitschieters in hoge maandbedragen is 0 of onder maximum aantal toegestaan</description>
        <inputEntry id="UnaryTests_05etf22">
          <text>true</text>
        </inputEntry>
        <inputEntry id="UnaryTests_1xxnciv">
          <text>false</text>
        </inputEntry>
        <outputEntry id="LiteralExpression_05kwni2">
          <text>true</text>
        </outputEntry>
      </rule>
      <rule id="DecisionRule_0bfsnny">
        <inputEntry id="UnaryTests_1vnzw7q">
          <text></text>
        </inputEntry>
        <inputEntry id="UnaryTests_0qhldil">
          <text></text>
        </inputEntry>
        <outputEntry id="LiteralExpression_0g67a62">
          <text>false</text>
        </outputEntry>
      </rule>
    </decisionTable>
  </decision>
  <inputData id="InputData_1pkrzk1" name="Ref:HoogInkomen" />
  <decision id="Decision_0ta8sug" name="TeveelMaandInkomenHoog">
    <variable id="InformationItem_1ap9eqh" typeRef="boolean" />
    <informationRequirement id="InformationRequirement_0i49fj5">
      <requiredInput href="#InputData_0sfd9vu" />
    </informationRequirement>
    <informationRequirement id="InformationRequirement_1cq7iit">
      <requiredInput href="#InputData_042i8cf" />
    </informationRequirement>
    <informationRequirement id="InformationRequirement_1lgl8hi">
      <requiredInput href="#InputData_1pkrzk1" />
    </informationRequirement>
    <literalExpression id="LiteralExpression_09q3b7e" expressionLanguage="feel">
      <text>Voor elk InkomenMaand
        Indien InkomenMaand &gt; Ref:HoogInkomen,
              Tel 1 op bij AantalMaanden


Indien AantalMaanden &gt; = Ref:TeveelMaandenHoog
        Dan  TeveelMaandenInkomenHoog = True
</text>
    </literalExpression>
  </decision>
  <decision id="Decision_021x8zv" name="GemiddeldInkomenLaag">
    <variable id="InformationItem_1v1tas2" />
    <informationRequirement id="InformationRequirement_0fp2m0i">
      <requiredInput href="#InputData_15d86p9" />
    </informationRequirement>
    <informationRequirement id="InformationRequirement_1lexqm7">
      <requiredInput href="#InputData_0jmf6og" />
    </informationRequirement>
    <informationRequirement id="InformationRequirement_0mrhhld">
      <requiredInput href="#InputData_0sfd9vu" />
    </informationRequirement>
    <literalExpression id="LiteralExpression_0a36xvt">
      <text>Tel alle bedragen InkomenMaand uit Ref:Referteperiode bij elkaar op tot InkomenTotaal
Deel InkomenTotaal door aantal maanden Ref:Referteperiode
Uitkomst = GemiddeldInkomen

Indien GemiddeldInkomen &lt; Ref:LaagInkomen
       dan GemiddeldInkomenLaag = True</text>
    </literalExpression>
  </decision>
  <textAnnotation id="TextAnnotation_1lr75gu">
    <text>1. Kenmerken IIT-regels: onafhankelijk van elkaar: als er 1 afgaat (afwijzing), beïnvloedt dat de anderen niet. Zo gemodelleerd dat het "elementaire" regels zijn. De complexiteit zit in de onderliggende blokjes / DMN-tabellen, en de vaststelservices daar weer onder / naast. 2Hit Policy = Multiple, type Collect. Er moeten meerdere regels af kunnen gaan, niet voldoen om bij ééntje te stoppen. Je wilt t.b.v transparantie en proces alle oordelen hebben (als de regel is afgegaan). 3. om te weten welke regel is "afgegaan" (negatief) is true/false niet voldoende, daarom ook een code meegeven. Voor elke regel die "negatief" afgaat (afwijzing), wordt een unieke  code opgeleverd, deze kan verder in het proces &amp; communicatie gebruikt worden.  4. tussentijds toetsing: je gebruikt alleen de input die je hebt, andere inputvelden blijven leeg "waarde = "-". De lege waardes hebben geen effect op de beslissing. 5. in DMN-tabel nog 1 regel voor de goed-situatie opgenomen: als alle input geleverd is (dus alle regels zijn getoetst op inhoud), dan expliciet Recht = Ja. Had eerst een "default-regel met "-"- waardes. Maar geeft denk ik verkeerde uitkomst: als dan niets ingevuld, dan ook recht. NB: ik kan me indenken dat hier best practices voor zijn. 6. effect op architectuur? eisen stellen aan hoe DMN-tabellen samen te structureren in beslisboom (hoe ver met opknippen), afspraken over hoe data te gebruiken (wanneer leeg laten, defaults gebruiken, etc. 7. Deze DMN werkt m.i. niet alleen voor tussentijdse toetsing, maar ook voor een besluit op de volledige gegevensset. 8. sturing vind plaats in de processturing: wanneer roep je deze service aan, met welke input. En wat doe je met het resultaat. Je kunt dit ok gebruiken om met alleen "Ja/Nee"vragen toch al een toets te doen: je vult dan handmatig de waardes in i.p.v. de vaststelservice+onderliggende DMN-tabellen (als "Leeftijd OK" te gebruiken om de input vast te stellen.</text>
  </textAnnotation>
  <association id="Association_0q1if87">
    <sourceRef href="#TextAnnotation_1lr75gu" />
    <targetRef href="#Decision_07xyq1v" />
  </association>
  <textAnnotation id="TextAnnotation_0uu7rug">
    <text>In de wet staat dat er een verzoek moet zijn. Meestal wordt dit geïnterpreteerd als dat er een formele aanvraag moet zijn, en dat weer vertaald naar of er een zaak is. Wanneer je dit criterium hard in de beslistabel ÏIT (art. 36)" opneemt, kan je de logica niet gebruiken bij informele aanvragen. Voorstel is om dit criterium WEL in de workflow op te nemen. Formeel Beslissen = vaststellen recht + aanwezig zijn verzoek.  Onderscheid vaststellen recht en beslissen. Hiermee kan je ook nog stappen als "coulance" of afwijkende beslissing inbouwen.</text>
  </textAnnotation>
  <association id="Association_11yym03">
    <sourceRef href="#TextAnnotation_0uu7rug" />
    <targetRef href="#Decision_1g1bgbm" />
  </association>
  <textAnnotation id="TextAnnotation_03w2rlg">
    <text>Vanuit de vaststelservice. Uitgangspunt: Vaststelservice stelt alleen het inkomen vast, per maand, over de referteperiode heen. Geeft geen oordeel over hoog/laag.  Deze logica zit elders</text>
  </textAnnotation>
  <association id="Association_1rpoppn">
    <sourceRef href="#TextAnnotation_03w2rlg" />
    <targetRef href="#InputData_0sfd9vu" />
  </association>
  <dmndi:DMNDI>
    <dmndi:DMNDiagram>
      <dmndi:DMNShape dmnElementRef="Decision_07xyq1v">
        <dc:Bounds height="80" width="180" x="1580" y="210" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_0iet30x" dmnElementRef="InformationRequirement_1twr4qf">
        <di:waypoint x="730" y="550" />
        <di:waypoint x="1634" y="310" />
        <di:waypoint x="1634" y="290" />
      </dmndi:DMNEdge>
      <dmndi:DMNEdge id="DMNEdge_000cdpw" dmnElementRef="InformationRequirement_1xqept6">
        <di:waypoint x="1010" y="550" />
        <di:waypoint x="1652" y="310" />
        <di:waypoint x="1652" y="290" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_12pojg8" dmnElementRef="Decision_1kggvgs">
        <dc:Bounds height="80" width="180" x="1170" y="550" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_1wv6rwk" dmnElementRef="InformationRequirement_19r1o08">
        <di:waypoint x="1260" y="550" />
        <di:waypoint x="1670" y="310" />
        <di:waypoint x="1670" y="290" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_1bba613" dmnElementRef="Decision_1o8uc3d">
        <dc:Bounds height="80" width="180" x="1170" y="730" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_0ycr159" dmnElementRef="InformationRequirement_02g71hk">
        <di:waypoint x="1260" y="730" />
        <di:waypoint x="1260" y="650" />
        <di:waypoint x="1260" y="630" />
      </dmndi:DMNEdge>
      <dmndi:DMNEdge id="DMNEdge_1ejudoi" dmnElementRef="InformationRequirement_1k28dub">
        <di:waypoint x="1510" y="550" />
        <di:waypoint x="1688" y="310" />
        <di:waypoint x="1688" y="290" />
      </dmndi:DMNEdge>
      <dmndi:DMNEdge id="DMNEdge_11w0k0l" dmnElementRef="InformationRequirement_1xifqtc">
        <di:waypoint x="1750" y="550" />
        <di:waypoint x="1706" y="310" />
        <di:waypoint x="1706" y="290" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_02zp7ta" dmnElementRef="Decision_1fpfrrv">
        <dc:Bounds height="80" width="180" x="1570" y="760" />
      </dmndi:DMNShape>
      <dmndi:DMNShape id="DMNShape_16tg041" dmnElementRef="Decision_1tjyffq">
        <dc:Bounds height="80" width="180" x="1800" y="850" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_18toxgx" dmnElementRef="InformationRequirement_1ie31bs">
        <di:waypoint x="490" y="550" />
        <di:waypoint x="1616" y="310" />
        <di:waypoint x="1616" y="290" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_0ibjnyk" dmnElementRef="Decision_0bbe0q4">
        <dc:Bounds height="80" width="180" x="1900" y="550" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_1o8wo23" dmnElementRef="InformationRequirement_1g2rusm">
        <di:waypoint x="1990" y="550" />
        <di:waypoint x="1724" y="310" />
        <di:waypoint x="1724" y="290" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_070qij1" dmnElementRef="Decision_04k6u1j">
        <dc:Bounds height="80" width="180" x="2460" y="560" />
      </dmndi:DMNShape>
      <dmndi:DMNShape id="DMNShape_0cvyum1" dmnElementRef="Decision_1y37ew1">
        <dc:Bounds height="80" width="180" x="2220" y="740" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_1sddgz2" dmnElementRef="InformationRequirement_0fuqu0p">
        <di:waypoint x="2310" y="740" />
        <di:waypoint x="2496" y="660" />
        <di:waypoint x="2496" y="640" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_1ilgcfa" dmnElementRef="Decision_006rxfz">
        <dc:Bounds height="80" width="180" x="2412" y="740" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_173rv3v" dmnElementRef="InformationRequirement_0nh3f8q">
        <di:waypoint x="2502" y="740" />
        <di:waypoint x="2532" y="660" />
        <di:waypoint x="2532" y="640" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_0qpve6q" dmnElementRef="Decision_09a41bd">
        <dc:Bounds height="80" width="180" x="2600" y="740" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_0j6igpk" dmnElementRef="InformationRequirement_1ktd6mg">
        <di:waypoint x="2690" y="740" />
        <di:waypoint x="2568" y="660" />
        <di:waypoint x="2568" y="640" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_1j8dwu3" dmnElementRef="Decision_0l5dyow">
        <dc:Bounds height="80" width="180" x="2790" y="740" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_1u18hxq" dmnElementRef="InformationRequirement_1fpls5m">
        <di:waypoint x="2880" y="740" />
        <di:waypoint x="2604" y="660" />
        <di:waypoint x="2604" y="640" />
      </dmndi:DMNEdge>
      <dmndi:DMNEdge id="DMNEdge_1kh2l65" dmnElementRef="InformationRequirement_0rc2sk1">
        <di:waypoint x="2550" y="560" />
        <di:waypoint x="1742" y="310" />
        <di:waypoint x="1742" y="290" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_0g26oi9" dmnElementRef="InputData_15d86p9">
        <dc:Bounds height="45" width="125" x="722" y="1117" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_1atkif2" dmnElementRef="InformationRequirement_0fp2m0i">
        <di:waypoint x="785" y="1117" />
        <di:waypoint x="805" y="1060" />
        <di:waypoint x="805" y="1040" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_0su2tjg" dmnElementRef="InputData_0jmf6og">
        <dc:Bounds height="45" width="125" x="777" y="1207" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_19hqxtm" dmnElementRef="InformationRequirement_1lexqm7">
        <di:waypoint x="840" y="1207" />
        <di:waypoint x="850" y="1060" />
        <di:waypoint x="850" y="1040" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_0d3c86q" dmnElementRef="InputData_0o0yzri">
        <dc:Bounds height="45" width="125" x="577" y="747" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_0nrk3z5" dmnElementRef="InformationRequirement_1k4lgbn">
        <di:waypoint x="640" y="747" />
        <di:waypoint x="700" y="650" />
        <di:waypoint x="700" y="630" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_0w90rdu" dmnElementRef="InputData_1vm5uj4">
        <dc:Bounds height="45" width="125" x="737" y="747" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_0jlq9dn" dmnElementRef="InformationRequirement_1vxg56b">
        <di:waypoint x="800" y="747" />
        <di:waypoint x="760" y="650" />
        <di:waypoint x="760" y="630" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_0fy8mdl" dmnElementRef="Decision_0k9cyom">
        <dc:Bounds height="80" width="180" x="1550" y="1190" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_0o8470c" dmnElementRef="InformationRequirement_1ufersf">
        <di:waypoint x="1640" y="1190" />
        <di:waypoint x="1290" y="830" />
        <di:waypoint x="1290" y="810" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_073mpco" dmnElementRef="InputData_1virsau">
        <dc:Bounds height="45" width="125" x="1027" y="1507" />
      </dmndi:DMNShape>
      <dmndi:DMNShape id="DMNShape_1h1kj26" dmnElementRef="InputData_149951b">
        <dc:Bounds height="45" width="125" x="1507" y="1397" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_0lj525u" dmnElementRef="InformationRequirement_02bh8dd">
        <di:waypoint x="1570" y="1397" />
        <di:waypoint x="1610" y="1290" />
        <di:waypoint x="1610" y="1270" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_1ljdbz7" dmnElementRef="Decision_1vafsvk">
        <dc:Bounds height="80" width="180" x="1010" y="1350" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_0zlz2tq" dmnElementRef="InformationRequirement_1vmve45">
        <di:waypoint x="1090" y="1507" />
        <di:waypoint x="1100" y="1450" />
        <di:waypoint x="1100" y="1430" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_0jphyoc" dmnElementRef="Decision_02mzepc">
        <dc:Bounds height="80" width="180" x="1190" y="1190" />
      </dmndi:DMNShape>
      <dmndi:DMNShape id="DMNShape_0wwbpnl" dmnElementRef="Decision_0wtqmfc">
        <dc:Bounds height="80" width="180" x="1660" y="1380" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_0fsmbjk" dmnElementRef="InformationRequirement_0paevvg">
        <di:waypoint x="1750" y="1380" />
        <di:waypoint x="1670" y="1290" />
        <di:waypoint x="1670" y="1270" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_01662fg" dmnElementRef="Decision_05ylk1d">
        <dc:Bounds height="80" width="180" x="1270" y="1350" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_1eqw0ci" dmnElementRef="InformationRequirement_0ber3g6">
        <di:waypoint x="1360" y="1350" />
        <di:waypoint x="1310" y="1290" />
        <di:waypoint x="1310" y="1270" />
      </dmndi:DMNEdge>
      <dmndi:DMNEdge id="DMNEdge_076ga17" dmnElementRef="InformationRequirement_13bdf5n">
        <di:waypoint x="1100" y="1350" />
        <di:waypoint x="1250" y="1290" />
        <di:waypoint x="1250" y="1270" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_0silwsz" dmnElementRef="Decision_19i2gri">
        <dc:Bounds height="80" width="180" x="1380" y="1540" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_1sizap8" dmnElementRef="InformationRequirement_0qp1tda">
        <di:waypoint x="1470" y="1540" />
        <di:waypoint x="1360" y="1450" />
        <di:waypoint x="1360" y="1430" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_0l0grl0" dmnElementRef="Decision_0jqfpdf">
        <dc:Bounds height="80" width="180" x="1160" y="1540" />
      </dmndi:DMNShape>
      <dmndi:DMNShape id="DMNShape_08rvrd2" dmnElementRef="InputData_17b8ac9">
        <dc:Bounds height="45" width="125" x="1027" y="1667" />
      </dmndi:DMNShape>
      <dmndi:DMNShape id="DMNShape_1f0d9nw" dmnElementRef="InputData_002pq02">
        <dc:Bounds height="45" width="125" x="1207" y="1717" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_09omgmg" dmnElementRef="InformationRequirement_15xp265">
        <di:waypoint x="1270" y="1717" />
        <di:waypoint x="1280" y="1640" />
        <di:waypoint x="1280" y="1620" />
      </dmndi:DMNEdge>
      <dmndi:DMNEdge id="DMNEdge_10c9yq8" dmnElementRef="InformationRequirement_0b6fsvh">
        <di:waypoint x="1090" y="1667" />
        <di:waypoint x="1220" y="1640" />
        <di:waypoint x="1220" y="1620" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_140cjh0" dmnElementRef="KnowledgeSource_18u6e4t">
        <dc:Bounds height="63" width="100" x="840" y="1668" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_0z4p5wc" dmnElementRef="AuthorityRequirement_1n2z1zd">
        <di:waypoint x="1027" y="1699" />
        <di:waypoint x="940" y="1676" />
      </dmndi:DMNEdge>
      <dmndi:DMNEdge id="DMNEdge_0p3ig99" dmnElementRef="InformationRequirement_17dfd2s">
        <di:waypoint x="1280" y="1190" />
        <di:waypoint x="1230" y="830" />
        <di:waypoint x="1230" y="810" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_0tspdrc" dmnElementRef="GBI-BR-001">
        <dc:Bounds height="80" width="180" x="640" y="550" />
      </dmndi:DMNShape>
      <dmndi:DMNShape id="DMNShape_1jvtqo6" dmnElementRef="Decision_1ac2lm5">
        <dc:Bounds height="80" width="180" x="1420" y="550" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_0jjlw16" dmnElementRef="InformationRequirement_15g8qce">
        <di:waypoint x="1660" y="760" />
        <di:waypoint x="1720" y="650" />
        <di:waypoint x="1720" y="630" />
      </dmndi:DMNEdge>
      <dmndi:DMNEdge id="DMNEdge_06vi259" dmnElementRef="InformationRequirement_1cu8t2z">
        <di:waypoint x="1890" y="850" />
        <di:waypoint x="1780" y="650" />
        <di:waypoint x="1780" y="630" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_0kgx7bv" dmnElementRef="Decision_1pqlsl4">
        <dc:Bounds height="80" width="180" x="1660" y="550" />
      </dmndi:DMNShape>
      <dmndi:DMNShape id="DMNShape_05a7ybe" dmnElementRef="TextAnnotation_1lr75gu">
        <dc:Bounds height="340" width="810" x="560" y="40" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_1xfwwph" dmnElementRef="Association_0q1if87">
        <di:waypoint x="1370" y="213" />
        <di:waypoint x="1580" y="239" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_1a7nebe" dmnElementRef="Decision_1g1bgbm">
        <dc:Bounds height="80" width="180" x="400" y="550" />
      </dmndi:DMNShape>
      <dmndi:DMNShape id="DMNShape_1g2tx5d" dmnElementRef="TextAnnotation_0uu7rug">
        <dc:Bounds height="150" width="400" x="250" y="315" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_0ixbahh" dmnElementRef="Association_11yym03">
        <di:waypoint x="346" y="465" />
        <di:waypoint x="444" y="550" />
      </dmndi:DMNEdge>
      <dmndi:DMNEdge id="DMNEdge_0u9u6bz" dmnElementRef="InformationRequirement_17un8ym">
        <di:waypoint x="250" y="550" />
        <di:waypoint x="1598" y="310" />
        <di:waypoint x="1598" y="290" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_1bkpcne" dmnElementRef="Decision_194g2m2">
        <dc:Bounds height="80" width="180" x="160" y="550" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_1i0362w" dmnElementRef="InformationRequirement_1m3iame">
        <di:waypoint x="850" y="960" />
        <di:waypoint x="980" y="650" />
        <di:waypoint x="980" y="630" />
      </dmndi:DMNEdge>
      <dmndi:DMNEdge id="DMNEdge_06kimta" dmnElementRef="InformationRequirement_0l6mumu">
        <di:waypoint x="1050" y="960" />
        <di:waypoint x="1040" y="650" />
        <di:waypoint x="1040" y="630" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_0exswrv" dmnElementRef="InputData_042i8cf">
        <dc:Bounds height="45" width="125" x="1037" y="1117" />
      </dmndi:DMNShape>
      <dmndi:DMNShape id="DMNShape_1av6ayd" dmnElementRef="InputData_0sfd9vu">
        <dc:Bounds height="45" width="125" x="887" y="1137" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_12f802b" dmnElementRef="InformationRequirement_0mrhhld">
        <di:waypoint x="950" y="1137" />
        <di:waypoint x="895" y="1060" />
        <di:waypoint x="895" y="1040" />
      </dmndi:DMNEdge>
      <dmndi:DMNEdge id="DMNEdge_1p9xxmf" dmnElementRef="InformationRequirement_0i49fj5">
        <di:waypoint x="950" y="1137" />
        <di:waypoint x="1005" y="1060" />
        <di:waypoint x="1005" y="1040" />
      </dmndi:DMNEdge>
      <dmndi:DMNEdge id="DMNEdge_1lskcp5" dmnElementRef="InformationRequirement_1cq7iit">
        <di:waypoint x="1100" y="1117" />
        <di:waypoint x="1095" y="1060" />
        <di:waypoint x="1095" y="1040" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_0o7g2w5" dmnElementRef="Decision_1abq2rm">
        <dc:Bounds height="80" width="180" x="920" y="550" />
      </dmndi:DMNShape>
      <dmndi:DMNShape id="DMNShape_1aq0sur" dmnElementRef="TextAnnotation_03w2rlg">
        <dc:Bounds height="100" width="327" x="440" y="1250" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_0dyo4sx" dmnElementRef="Association_1rpoppn">
        <di:waypoint x="731" y="1250" />
        <di:waypoint x="894" y="1182" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_1grlgj8" dmnElementRef="InputData_1pkrzk1">
        <dc:Bounds height="45" width="125" x="987" y="1207" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_0nlzlce" dmnElementRef="InformationRequirement_1lgl8hi">
        <di:waypoint x="1050" y="1207" />
        <di:waypoint x="1050" y="1060" />
        <di:waypoint x="1050" y="1040" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_1vixswo" dmnElementRef="Decision_0ta8sug">
        <dc:Bounds height="80" width="180" x="960" y="960" />
      </dmndi:DMNShape>
      <dmndi:DMNShape id="DMNShape_0a2t5o2" dmnElementRef="Decision_021x8zv">
        <dc:Bounds height="80" width="180" x="760" y="960" />
      </dmndi:DMNShape>
    </dmndi:DMNDiagram>
  </dmndi:DMNDI>
</definitions>', 'UTF8'), null, null, 'Mark Akkermans', '2025-09-24 15:21:33.499993');
INSERT INTO public.versions (dmn_id, version, status, file_blob, modified_by, modified_date, created_by, created_date) VALUES (2, 1, 4, convert_to('<?xml version="1.0" encoding="UTF-8"?>
            <definitions xmlns="https://www.omg.org/spec/DMN/20191111/MODEL/" xmlns:dmndi="https://www.omg.org/spec/DMN/20191111/DMNDI/" xmlns:dc="http://www.omg.org/spec/DMN/20180521/DC/" id="Jeugdzorgindicatie" name="Jeugdzorg-indicatie" namespace="http://camunda.org/schema/1.0/dmn" exporter="dmn-js (https://demo.bpmn.io/dmn)" exporterVersion="17.2.0">
              <decision id="decision_19w0x1w" name="">
                <decisionTable id="decisionTable_16f4dkd">
                  <input id="input1" label="">
                    <inputExpression id="inputExpression1" typeRef="string">
                      <text></text>
                    </inputExpression>
                  </input>
                  <output id="output1" label="" name="" typeRef="string" />
                </decisionTable>
              </decision>
              <dmndi:DMNDI>
                <dmndi:DMNDiagram id="DMNDiagram_0k7uk6v">
                  <dmndi:DMNShape id="DMNShape_1tqq611" dmnElementRef="decision_19w0x1w">
                    <dc:Bounds height="80" width="180" x="150" y="80" />
                  </dmndi:DMNShape>
                </dmndi:DMNDiagram>
              </dmndi:DMNDI>
            </definitions>', 'UTF8'), null, null, 'Mark Akkermans', '2025-09-24 13:56:11.324782');
INSERT INTO public.versions (dmn_id, version, status, file_blob, modified_by, modified_date, created_by, created_date) VALUES (3, 1, 1, convert_to('<?xml version="1.0" encoding="UTF-8"?>
            <definitions xmlns="https://www.omg.org/spec/DMN/20191111/MODEL/" xmlns:dmndi="https://www.omg.org/spec/DMN/20191111/DMNDI/" xmlns:dc="http://www.omg.org/spec/DMN/20180521/DC/" id="Inkomensondersteuning" name="Inkomensondersteuning" namespace="http://camunda.org/schema/1.0/dmn" exporter="dmn-js (https://demo.bpmn.io/dmn)" exporterVersion="17.2.0">
              <decision id="decision_19w0x1w" name="">
                <decisionTable id="decisionTable_16f4dkd">
                  <input id="input1" label="">
                    <inputExpression id="inputExpression1" typeRef="string">
                      <text></text>
                    </inputExpression>
                  </input>
                  <output id="output1" label="" name="" typeRef="string" />
                </decisionTable>
              </decision>
              <dmndi:DMNDI>
                <dmndi:DMNDiagram id="DMNDiagram_0k7uk6v">
                  <dmndi:DMNShape id="DMNShape_1tqq611" dmnElementRef="decision_19w0x1w">
                    <dc:Bounds height="80" width="180" x="150" y="80" />
                  </dmndi:DMNShape>
                </dmndi:DMNDiagram>
              </dmndi:DMNDI>
            </definitions>', 'UTF8'), null, null, 'Mark Akkermans', '2025-09-24 13:56:26.427975');
INSERT INTO public.versions (dmn_id, version, status, file_blob, modified_by, modified_date, created_by, created_date) VALUES (5, 1, 1, convert_to('<?xml version="1.0" encoding="UTF-8"?>
            <definitions xmlns="https://www.omg.org/spec/DMN/20191111/MODEL/" xmlns:dmndi="https://www.omg.org/spec/DMN/20191111/DMNDI/" xmlns:dc="http://www.omg.org/spec/DMN/20180521/DC/" id="Eigen_bijdrage_berekening" name="Eigen bijdrage berekening" namespace="http://camunda.org/schema/1.0/dmn" exporter="dmn-js (https://demo.bpmn.io/dmn)" exporterVersion="17.2.0">
              <decision id="decision_19w0x1w" name="">
                <decisionTable id="decisionTable_16f4dkd">
                  <input id="input1" label="">
                    <inputExpression id="inputExpression1" typeRef="string">
                      <text></text>
                    </inputExpression>
                  </input>
                  <output id="output1" label="" name="" typeRef="string" />
                </decisionTable>
              </decision>
              <dmndi:DMNDI>
                <dmndi:DMNDiagram id="DMNDiagram_0k7uk6v">
                  <dmndi:DMNShape id="DMNShape_1tqq611" dmnElementRef="decision_19w0x1w">
                    <dc:Bounds height="80" width="180" x="150" y="80" />
                  </dmndi:DMNShape>
                </dmndi:DMNDiagram>
              </dmndi:DMNDI>
            </definitions>', 'UTF8'), null, null, 'Mark Akkermans', '2025-09-24 13:56:53.690603');
INSERT INTO public.versions (dmn_id, version, status, file_blob, modified_by, modified_date, created_by, created_date) VALUES (4, 1, 1, convert_to('<?xml version="1.0" encoding="UTF-8"?>
            <definitions xmlns="https://www.omg.org/spec/DMN/20191111/MODEL/" xmlns:dmndi="https://www.omg.org/spec/DMN/20191111/DMNDI/" xmlns:dc="http://www.omg.org/spec/DMN/20180521/DC/" id="Participatieplicht" name="Participatieplicht" namespace="http://camunda.org/schema/1.0/dmn" exporter="dmn-js (https://demo.bpmn.io/dmn)" exporterVersion="17.2.0">
              <decision id="decision_19w0x1w" name="">
                <decisionTable id="decisionTable_16f4dkd">
                  <input id="input1" label="">
                    <inputExpression id="inputExpression1" typeRef="string">
                      <text></text>
                    </inputExpression>
                  </input>
                  <output id="output1" label="" name="" typeRef="string" />
                </decisionTable>
              </decision>
              <dmndi:DMNDI>
                <dmndi:DMNDiagram id="DMNDiagram_0k7uk6v">
                  <dmndi:DMNShape id="DMNShape_1tqq611" dmnElementRef="decision_19w0x1w">
                    <dc:Bounds height="80" width="180" x="150" y="80" />
                  </dmndi:DMNShape>
                </dmndi:DMNDiagram>
              </dmndi:DMNDI>
            </definitions>', 'UTF8'), null, null, 'Mark Akkermans', '2025-09-24 13:56:39.318429');
INSERT INTO public.versions (dmn_id, version, status, file_blob, modified_by, modified_date, created_by, created_date) VALUES (14, 1, 1, convert_to('<?xml version="1.0" encoding="UTF-8"?>
            <definitions xmlns="https://www.omg.org/spec/DMN/20191111/MODEL/" xmlns:dmndi="https://www.omg.org/spec/DMN/20191111/DMNDI/" xmlns:dc="http://www.omg.org/spec/DMN/20180521/DC/" id="Vergaderquorum" name="Vergaderquorum" namespace="http://camunda.org/schema/1.0/dmn" exporter="dmn-js (https://demo.bpmn.io/dmn)" exporterVersion="17.2.0">
              <decision id="decision_19w0x1w" name="">
                <decisionTable id="decisionTable_16f4dkd">
                  <input id="input1" label="">
                    <inputExpression id="inputExpression1" typeRef="string">
                      <text></text>
                    </inputExpression>
                  </input>
                  <output id="output1" label="" name="" typeRef="string" />
                </decisionTable>
              </decision>
              <dmndi:DMNDI>
                <dmndi:DMNDiagram id="DMNDiagram_0k7uk6v">
                  <dmndi:DMNShape id="DMNShape_1tqq611" dmnElementRef="decision_19w0x1w">
                    <dc:Bounds height="80" width="180" x="150" y="80" />
                  </dmndi:DMNShape>
                </dmndi:DMNDiagram>
              </dmndi:DMNDI>
            </definitions>', 'UTF8'), null, null, 'Mark Akkermans', '2025-09-24 13:59:12.797640');
INSERT INTO public.versions (dmn_id, version, status, file_blob, modified_by, modified_date, created_by, created_date) VALUES (6, 1, 1, convert_to('<?xml version="1.0" encoding="UTF-8"?>
            <definitions xmlns="https://www.omg.org/spec/DMN/20191111/MODEL/" xmlns:dmndi="https://www.omg.org/spec/DMN/20191111/DMNDI/" xmlns:dc="http://www.omg.org/spec/DMN/20180521/DC/" id="Vergunning_evenementen" name="Vergunning evenementen" namespace="http://camunda.org/schema/1.0/dmn" exporter="dmn-js (https://demo.bpmn.io/dmn)" exporterVersion="17.2.0">
              <decision id="decision_19w0x1w" name="">
                <decisionTable id="decisionTable_16f4dkd">
                  <input id="input1" label="">
                    <inputExpression id="inputExpression1" typeRef="string">
                      <text></text>
                    </inputExpression>
                  </input>
                  <output id="output1" label="" name="" typeRef="string" />
                </decisionTable>
              </decision>
              <dmndi:DMNDI>
                <dmndi:DMNDiagram id="DMNDiagram_0k7uk6v">
                  <dmndi:DMNShape id="DMNShape_1tqq611" dmnElementRef="decision_19w0x1w">
                    <dc:Bounds height="80" width="180" x="150" y="80" />
                  </dmndi:DMNShape>
                </dmndi:DMNDiagram>
              </dmndi:DMNDI>
            </definitions>', 'UTF8'), null, null, 'Mark Akkermans', '2025-09-24 13:57:10.176194');
INSERT INTO public.versions (dmn_id, version, status, file_blob, modified_by, modified_date, created_by, created_date) VALUES (12, 1, 1, convert_to('<?xml version="1.0" encoding="UTF-8"?>
            <definitions xmlns="https://www.omg.org/spec/DMN/20191111/MODEL/" xmlns:dmndi="https://www.omg.org/spec/DMN/20191111/DMNDI/" xmlns:dc="http://www.omg.org/spec/DMN/20180521/DC/" id="Inspraakrecht" name="Inspraakrecht" namespace="http://camunda.org/schema/1.0/dmn" exporter="dmn-js (https://demo.bpmn.io/dmn)" exporterVersion="17.2.0">
              <decision id="decision_19w0x1w" name="">
                <decisionTable id="decisionTable_16f4dkd">
                  <input id="input1" label="">
                    <inputExpression id="inputExpression1" typeRef="string">
                      <text></text>
                    </inputExpression>
                  </input>
                  <output id="output1" label="" name="" typeRef="string" />
                </decisionTable>
              </decision>
              <dmndi:DMNDI>
                <dmndi:DMNDiagram id="DMNDiagram_0k7uk6v">
                  <dmndi:DMNShape id="DMNShape_1tqq611" dmnElementRef="decision_19w0x1w">
                    <dc:Bounds height="80" width="180" x="150" y="80" />
                  </dmndi:DMNShape>
                </dmndi:DMNDiagram>
              </dmndi:DMNDI>
            </definitions>', 'UTF8'), null, null, 'Mark Akkermans', '2025-09-24 13:58:49.165385');
INSERT INTO public.versions (dmn_id, version, status, file_blob, modified_by, modified_date, created_by, created_date) VALUES (10, 1, 1, convert_to('<?xml version="1.0" encoding="UTF-8"?>
            <definitions xmlns="https://www.omg.org/spec/DMN/20191111/MODEL/" xmlns:dmndi="https://www.omg.org/spec/DMN/20191111/DMNDI/" xmlns:dc="http://www.omg.org/spec/DMN/20180521/DC/" id="Precariobelasting_vrijstelling" name="Precariobelasting vrijstelling" namespace="http://camunda.org/schema/1.0/dmn" exporter="dmn-js (https://demo.bpmn.io/dmn)" exporterVersion="17.2.0">
              <decision id="decision_19w0x1w" name="">
                <decisionTable id="decisionTable_16f4dkd">
                  <input id="input1" label="">
                    <inputExpression id="inputExpression1" typeRef="string">
                      <text></text>
                    </inputExpression>
                  </input>
                  <output id="output1" label="" name="" typeRef="string" />
                </decisionTable>
              </decision>
              <dmndi:DMNDI>
                <dmndi:DMNDiagram id="DMNDiagram_0k7uk6v">
                  <dmndi:DMNShape id="DMNShape_1tqq611" dmnElementRef="decision_19w0x1w">
                    <dc:Bounds height="80" width="180" x="150" y="80" />
                  </dmndi:DMNShape>
                </dmndi:DMNDiagram>
              </dmndi:DMNDI>
            </definitions>', 'UTF8'), null, null, 'Mark Akkermans', '2025-09-24 13:57:57.968053');
INSERT INTO public.versions (dmn_id, version, status, file_blob, modified_by, modified_date, created_by, created_date) VALUES (11, 1, 1, convert_to('<?xml version="1.0" encoding="UTF-8"?>
            <definitions xmlns="https://www.omg.org/spec/DMN/20191111/MODEL/" xmlns:dmndi="https://www.omg.org/spec/DMN/20191111/DMNDI/" xmlns:dc="http://www.omg.org/spec/DMN/20180521/DC/" id="Bezwaarbeoordeling" name="Bezwaarbeoordeling" namespace="http://camunda.org/schema/1.0/dmn" exporter="dmn-js (https://demo.bpmn.io/dmn)" exporterVersion="17.2.0">
              <decision id="decision_19w0x1w" name="">
                <decisionTable id="decisionTable_16f4dkd">
                  <input id="input1" label="">
                    <inputExpression id="inputExpression1" typeRef="string">
                      <text></text>
                    </inputExpression>
                  </input>
                  <output id="output1" label="" name="" typeRef="string" />
                </decisionTable>
              </decision>
              <dmndi:DMNDI>
                <dmndi:DMNDiagram id="DMNDiagram_0k7uk6v">
                  <dmndi:DMNShape id="DMNShape_1tqq611" dmnElementRef="decision_19w0x1w">
                    <dc:Bounds height="80" width="180" x="150" y="80" />
                  </dmndi:DMNShape>
                </dmndi:DMNDiagram>
              </dmndi:DMNDI>
            </definitions>', 'UTF8'), null, null, 'Mark Akkermans', '2025-09-24 13:58:36.356984');
INSERT INTO public.versions (dmn_id, version, status, file_blob, modified_by, modified_date, created_by, created_date) VALUES (8, 1, 1, convert_to('<?xml version="1.0" encoding="UTF-8"?>
            <definitions xmlns="https://www.omg.org/spec/DMN/20191111/MODEL/" xmlns:dmndi="https://www.omg.org/spec/DMN/20191111/DMNDI/" xmlns:dc="http://www.omg.org/spec/DMN/20180521/DC/" id="Standplaatsvergunning" name="Standplaatsvergunning" namespace="http://camunda.org/schema/1.0/dmn" exporter="dmn-js (https://demo.bpmn.io/dmn)" exporterVersion="17.2.0">
              <decision id="decision_19w0x1w" name="">
                <decisionTable id="decisionTable_16f4dkd">
                  <input id="input1" label="">
                    <inputExpression id="inputExpression1" typeRef="string">
                      <text></text>
                    </inputExpression>
                  </input>
                  <output id="output1" label="" name="" typeRef="string" />
                </decisionTable>
              </decision>
              <dmndi:DMNDI>
                <dmndi:DMNDiagram id="DMNDiagram_0k7uk6v">
                  <dmndi:DMNShape id="DMNShape_1tqq611" dmnElementRef="decision_19w0x1w">
                    <dc:Bounds height="80" width="180" x="150" y="80" />
                  </dmndi:DMNShape>
                </dmndi:DMNDiagram>
              </dmndi:DMNDI>
            </definitions>', 'UTF8'), null, null, 'Mark Akkermans', '2025-09-24 13:57:32.175094');
INSERT INTO public.versions (dmn_id, version, status, file_blob, modified_by, modified_date, created_by, created_date) VALUES (9, 1, 1, convert_to('<?xml version="1.0" encoding="UTF-8"?>
            <definitions xmlns="https://www.omg.org/spec/DMN/20191111/MODEL/" xmlns:dmndi="https://www.omg.org/spec/DMN/20191111/DMNDI/" xmlns:dc="http://www.omg.org/spec/DMN/20180521/DC/" id="Toewijzing_bedrijventerrein" name="Toewijzing bedrijventerrein" namespace="http://camunda.org/schema/1.0/dmn" exporter="dmn-js (https://demo.bpmn.io/dmn)" exporterVersion="17.2.0">
              <decision id="decision_19w0x1w" name="">
                <decisionTable id="decisionTable_16f4dkd">
                  <input id="input1" label="">
                    <inputExpression id="inputExpression1" typeRef="string">
                      <text></text>
                    </inputExpression>
                  </input>
                  <output id="output1" label="" name="" typeRef="string" />
                </decisionTable>
              </decision>
              <dmndi:DMNDI>
                <dmndi:DMNDiagram id="DMNDiagram_0k7uk6v">
                  <dmndi:DMNShape id="DMNShape_1tqq611" dmnElementRef="decision_19w0x1w">
                    <dc:Bounds height="80" width="180" x="150" y="80" />
                  </dmndi:DMNShape>
                </dmndi:DMNDiagram>
              </dmndi:DMNDI>
            </definitions>', 'UTF8'), null, null, 'Mark Akkermans', '2025-09-24 13:57:46.251806');
INSERT INTO public.versions (dmn_id, version, status, file_blob, modified_by, modified_date, created_by, created_date) VALUES (7, 1, 1, convert_to('<?xml version="1.0" encoding="UTF-8"?>
            <definitions xmlns="https://www.omg.org/spec/DMN/20191111/MODEL/" xmlns:dmndi="https://www.omg.org/spec/DMN/20191111/DMNDI/" xmlns:dc="http://www.omg.org/spec/DMN/20180521/DC/" id="Subsidietoekenning_bedrijven" name="Subsidietoekenning bedrijven" namespace="http://camunda.org/schema/1.0/dmn" exporter="dmn-js (https://demo.bpmn.io/dmn)" exporterVersion="17.2.0">
              <decision id="decision_19w0x1w" name="">
                <decisionTable id="decisionTable_16f4dkd">
                  <input id="input1" label="">
                    <inputExpression id="inputExpression1" typeRef="string">
                      <text></text>
                    </inputExpression>
                  </input>
                  <output id="output1" label="" name="" typeRef="string" />
                </decisionTable>
              </decision>
              <dmndi:DMNDI>
                <dmndi:DMNDiagram id="DMNDiagram_0k7uk6v">
                  <dmndi:DMNShape id="DMNShape_1tqq611" dmnElementRef="decision_19w0x1w">
                    <dc:Bounds height="80" width="180" x="150" y="80" />
                  </dmndi:DMNShape>
                </dmndi:DMNDiagram>
              </dmndi:DMNDI>
            </definitions>', 'UTF8'), null, null, 'Mark Akkermans', '2025-09-24 13:57:21.364542');
INSERT INTO public.versions (dmn_id, version, status, file_blob, modified_by, modified_date, created_by, created_date) VALUES (15, 1, 1, convert_to('<?xml version="1.0" encoding="UTF-8"?>
            <definitions xmlns="https://www.omg.org/spec/DMN/20191111/MODEL/" xmlns:dmndi="https://www.omg.org/spec/DMN/20191111/DMNDI/" xmlns:dc="http://www.omg.org/spec/DMN/20180521/DC/" id="Integriteitscheck" name="Integriteitscheck" namespace="http://camunda.org/schema/1.0/dmn" exporter="dmn-js (https://demo.bpmn.io/dmn)" exporterVersion="17.2.0">
              <decision id="decision_19w0x1w" name="">
                <decisionTable id="decisionTable_16f4dkd">
                  <input id="input1" label="">
                    <inputExpression id="inputExpression1" typeRef="string">
                      <text></text>
                    </inputExpression>
                  </input>
                  <output id="output1" label="" name="" typeRef="string" />
                </decisionTable>
              </decision>
              <dmndi:DMNDI>
                <dmndi:DMNDiagram id="DMNDiagram_0k7uk6v">
                  <dmndi:DMNShape id="DMNShape_1tqq611" dmnElementRef="decision_19w0x1w">
                    <dc:Bounds height="80" width="180" x="150" y="80" />
                  </dmndi:DMNShape>
                </dmndi:DMNDiagram>
              </dmndi:DMNDI>
            </definitions>', 'UTF8'), null, null, 'Mark Akkermans', '2025-09-24 13:59:28.074574');
INSERT INTO public.versions (dmn_id, version, status, file_blob, modified_by, modified_date, created_by, created_date) VALUES (13, 1, 1, convert_to('<?xml version="1.0" encoding="UTF-8"?>
            <definitions xmlns="https://www.omg.org/spec/DMN/20191111/MODEL/" xmlns:dmndi="https://www.omg.org/spec/DMN/20191111/DMNDI/" xmlns:dc="http://www.omg.org/spec/DMN/20180521/DC/" id="Subsidiebeoordeling_verenigingen" name="Subsidiebeoordeling verenigingen" namespace="http://camunda.org/schema/1.0/dmn" exporter="dmn-js (https://demo.bpmn.io/dmn)" exporterVersion="17.2.0">
              <decision id="decision_19w0x1w" name="">
                <decisionTable id="decisionTable_16f4dkd">
                  <input id="input1" label="">
                    <inputExpression id="inputExpression1" typeRef="string">
                      <text></text>
                    </inputExpression>
                  </input>
                  <output id="output1" label="" name="" typeRef="string" />
                </decisionTable>
              </decision>
              <dmndi:DMNDI>
                <dmndi:DMNDiagram id="DMNDiagram_0k7uk6v">
                  <dmndi:DMNShape id="DMNShape_1tqq611" dmnElementRef="decision_19w0x1w">
                    <dc:Bounds height="80" width="180" x="150" y="80" />
                  </dmndi:DMNShape>
                </dmndi:DMNDiagram>
              </dmndi:DMNDI>
            </definitions>', 'UTF8'), null, null, 'Mark Akkermans', '2025-09-24 13:59:02.016192');
INSERT INTO public.versions (dmn_id, version, status, file_blob, modified_by, modified_date, created_by, created_date) VALUES (1, 1, 5, convert_to('<?xml version="1.0" encoding="UTF-8"?>
            <definitions xmlns="https://www.omg.org/spec/DMN/20191111/MODEL/" xmlns:dmndi="https://www.omg.org/spec/DMN/20191111/DMNDI/" xmlns:dc="http://www.omg.org/spec/DMN/20180521/DC/" id="Toekenning_Wmovoorziening" name="Toekenning Wmo-voorziening" namespace="http://camunda.org/schema/1.0/dmn" exporter="dmn-js (https://demo.bpmn.io/dmn)" exporterVersion="17.2.0">
              <decision id="decision_19w0x1w" name="">
                <decisionTable id="decisionTable_16f4dkd">
                  <input id="input1" label="">
                    <inputExpression id="inputExpression1" typeRef="string">
                      <text></text>
                    </inputExpression>
                  </input>
                  <output id="output1" label="" name="" typeRef="string" />
                </decisionTable>
              </decision>
              <dmndi:DMNDI>
                <dmndi:DMNDiagram id="DMNDiagram_0k7uk6v">
                  <dmndi:DMNShape id="DMNShape_1tqq611" dmnElementRef="decision_19w0x1w">
                    <dc:Bounds height="80" width="180" x="150" y="80" />
                  </dmndi:DMNShape>
                </dmndi:DMNDiagram>
              </dmndi:DMNDI>
            </definitions>', 'UTF8'), null, null, 'Mark Akkermans', '2025-09-24 13:55:53.863178');
INSERT INTO public.versions (dmn_id, version, status, file_blob, modified_by, modified_date, created_by, created_date) VALUES (1, 2, 5, convert_to('<?xml version="1.0" encoding="UTF-8"?>
            <definitions xmlns="https://www.omg.org/spec/DMN/20191111/MODEL/" xmlns:dmndi="https://www.omg.org/spec/DMN/20191111/DMNDI/" xmlns:dc="http://www.omg.org/spec/DMN/20180521/DC/" id="Toekenning_Wmovoorziening" name="Toekenning Wmo-voorziening" namespace="http://camunda.org/schema/1.0/dmn" exporter="dmn-js (https://demo.bpmn.io/dmn)" exporterVersion="17.2.0">
              <decision id="decision_19w0x1w" name="">
                <decisionTable id="decisionTable_16f4dkd">
                  <input id="input1" label="">
                    <inputExpression id="inputExpression1" typeRef="string">
                      <text></text>
                    </inputExpression>
                  </input>
                  <output id="output1" label="" name="" typeRef="string" />
                </decisionTable>
              </decision>
              <dmndi:DMNDI>
                <dmndi:DMNDiagram id="DMNDiagram_0k7uk6v">
                  <dmndi:DMNShape id="DMNShape_1tqq611" dmnElementRef="decision_19w0x1w">
                    <dc:Bounds height="80" width="180" x="150" y="80" />
                  </dmndi:DMNShape>
                </dmndi:DMNDiagram>
              </dmndi:DMNDI>
            </definitions>', 'UTF8'), null, null, 'Mark Akkermans', '2025-09-24 14:16:56.476680');
INSERT INTO public.versions (dmn_id, version, status, file_blob, modified_by, modified_date, created_by, created_date) VALUES (1, 3, 4, convert_to('<?xml version="1.0" encoding="UTF-8"?>
<definitions xmlns="https://www.omg.org/spec/DMN/20191111/MODEL/" xmlns:dmndi="https://www.omg.org/spec/DMN/20191111/DMNDI/" xmlns:dc="http://www.omg.org/spec/DMN/20180521/DC/" xmlns:di="http://www.omg.org/spec/DMN/20180521/DI/" id="Toekenning_Wmovoorziening" name="Toekenning Wmo-voorziening" namespace="http://camunda.org/schema/1.0/dmn" exporter="dmn-js (https://demo.bpmn.io/dmn)" exporterVersion="17.2.0">
  <decision id="decision_19w0x1w" name="Wet DMN">
    <informationRequirement id="InformationRequirement_11udqmu">
      <requiredDecision href="#Decision_08nurrd" />
    </informationRequirement>
    <informationRequirement id="InformationRequirement_0l9jhnj">
      <requiredDecision href="#Decision_078zyed" />
    </informationRequirement>
    <decisionTable id="decisionTable_16f4dkd">
      <input id="input1" label="">
        <inputExpression id="inputExpression1" typeRef="string">
          <text></text>
        </inputExpression>
      </input>
      <output id="output1" label="" name="" typeRef="string" />
    </decisionTable>
  </decision>
  <decision id="Decision_078zyed">
    <informationRequirement id="InformationRequirement_19mx1op">
      <requiredInput href="#InputData_0y2rwve" />
    </informationRequirement>
    <informationRequirement id="InformationRequirement_07fv286">
      <requiredInput href="#InputData_14l4wpk" />
    </informationRequirement>
    <decisionTable id="DecisionTable_0d2xk4q">
      <input id="InputClause_066s3fb">
        <inputExpression id="LiteralExpression_1p4z569" typeRef="string" />
      </input>
      <output id="OutputClause_0scfvli" typeRef="string" />
    </decisionTable>
  </decision>
  <decision id="Decision_08nurrd">
    <informationRequirement id="InformationRequirement_18ydds0">
      <requiredInput href="#InputData_0u87v3z" />
    </informationRequirement>
    <informationRequirement id="InformationRequirement_06d2e8k">
      <requiredInput href="#InputData_15v4j8v" />
    </informationRequirement>
    <decisionTable id="DecisionTable_19x14al">
      <input id="InputClause_0xschxl">
        <inputExpression id="LiteralExpression_04re15w" typeRef="string" />
      </input>
      <output id="OutputClause_16d1k2x" typeRef="string" />
    </decisionTable>
  </decision>
  <inputData id="InputData_0u87v3z" />
  <inputData id="InputData_15v4j8v" />
  <inputData id="InputData_0y2rwve" />
  <inputData id="InputData_14l4wpk" />
  <knowledgeSource id="KnowledgeSource_0oyjdlc">
    <authorityRequirement id="AuthorityRequirement_0wlye79">
      <requiredInput href="#InputData_0u87v3z" />
    </authorityRequirement>
  </knowledgeSource>
  <knowledgeSource id="KnowledgeSource_08qyccr">
    <authorityRequirement id="AuthorityRequirement_1emak3i">
      <requiredInput href="#InputData_15v4j8v" />
    </authorityRequirement>
  </knowledgeSource>
  <knowledgeSource id="KnowledgeSource_0rtwf99">
    <authorityRequirement id="AuthorityRequirement_1qnfsxs">
      <requiredInput href="#InputData_0y2rwve" />
    </authorityRequirement>
  </knowledgeSource>
  <knowledgeSource id="KnowledgeSource_0ze30e4">
    <authorityRequirement id="AuthorityRequirement_0b0le9x">
      <requiredInput href="#InputData_14l4wpk" />
    </authorityRequirement>
  </knowledgeSource>
  <dmndi:DMNDI>
    <dmndi:DMNDiagram id="DMNDiagram_0k7uk6v">
      <dmndi:DMNShape id="DMNShape_1tqq611" dmnElementRef="decision_19w0x1w">
        <dc:Bounds height="80" width="180" x="400" y="120" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_1kt692q" dmnElementRef="InformationRequirement_11udqmu">
        <di:waypoint x="300" y="290" />
        <di:waypoint x="460" y="220" />
        <di:waypoint x="460" y="200" />
      </dmndi:DMNEdge>
      <dmndi:DMNEdge id="DMNEdge_198cdza" dmnElementRef="InformationRequirement_0l9jhnj">
        <di:waypoint x="630" y="290" />
        <di:waypoint x="520" y="220" />
        <di:waypoint x="520" y="200" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_05gjqfl" dmnElementRef="Decision_078zyed">
        <dc:Bounds height="80" width="180" x="540" y="290" />
      </dmndi:DMNShape>
      <dmndi:DMNShape id="DMNShape_1nlt2os" dmnElementRef="Decision_08nurrd">
        <dc:Bounds height="80" width="180" x="210" y="290" />
      </dmndi:DMNShape>
      <dmndi:DMNShape id="DMNShape_0ccqegn" dmnElementRef="InputData_0u87v3z">
        <dc:Bounds height="45" width="125" x="137" y="417" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_1p5i6mt" dmnElementRef="InformationRequirement_18ydds0">
        <di:waypoint x="200" y="417" />
        <di:waypoint x="270" y="390" />
        <di:waypoint x="270" y="370" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_15p9qf3" dmnElementRef="InputData_15v4j8v">
        <dc:Bounds height="45" width="125" x="317" y="417" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_10d2b2z" dmnElementRef="InformationRequirement_06d2e8k">
        <di:waypoint x="380" y="417" />
        <di:waypoint x="330" y="390" />
        <di:waypoint x="330" y="370" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_11ukma8" dmnElementRef="InputData_0y2rwve">
        <dc:Bounds height="45" width="125" x="487" y="417" />
      </dmndi:DMNShape>
      <dmndi:DMNShape id="DMNShape_1sxm5gs" dmnElementRef="InputData_14l4wpk">
        <dc:Bounds height="45" width="125" x="648" y="417" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_15mldeb" dmnElementRef="InformationRequirement_19mx1op">
        <di:waypoint x="550" y="417" />
        <di:waypoint x="600" y="390" />
        <di:waypoint x="600" y="370" />
      </dmndi:DMNEdge>
      <dmndi:DMNEdge id="DMNEdge_0ox9toa" dmnElementRef="InformationRequirement_07fv286">
        <di:waypoint x="711" y="417" />
        <di:waypoint x="660" y="390" />
        <di:waypoint x="660" y="370" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_0b9mysr" dmnElementRef="KnowledgeSource_0oyjdlc">
        <dc:Bounds height="63" width="100" x="150" y="499" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_0j3ah77" dmnElementRef="AuthorityRequirement_0wlye79">
        <di:waypoint x="200" y="462" />
        <di:waypoint x="200" y="499" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_069cxi0" dmnElementRef="KnowledgeSource_08qyccr">
        <dc:Bounds height="63" width="100" x="330" y="500" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_1h024qy" dmnElementRef="AuthorityRequirement_1emak3i">
        <di:waypoint x="380" y="462" />
        <di:waypoint x="380" y="500" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_0asgsm4" dmnElementRef="KnowledgeSource_0rtwf99">
        <dc:Bounds height="63" width="100" x="500" y="500" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_19fb1uh" dmnElementRef="AuthorityRequirement_1qnfsxs">
        <di:waypoint x="550" y="462" />
        <di:waypoint x="550" y="500" />
      </dmndi:DMNEdge>
      <dmndi:DMNShape id="DMNShape_0ud33to" dmnElementRef="KnowledgeSource_0ze30e4">
        <dc:Bounds height="63" width="100" x="661" y="500" />
      </dmndi:DMNShape>
      <dmndi:DMNEdge id="DMNEdge_158495n" dmnElementRef="AuthorityRequirement_0b0le9x">
        <di:waypoint x="710" y="462" />
        <di:waypoint x="711" y="500" />
      </dmndi:DMNEdge>
    </dmndi:DMNDiagram>
  </dmndi:DMNDI>
</definitions>
', 'UTF8'), 'Mark Akkermans', '2025-09-24 15:20:47.107494', 'Mark Akkermans', '2025-09-24 14:18:41.305405');
INSERT INTO public.versions (dmn_id, version, status, file_blob, modified_by, modified_date, created_by, created_date) VALUES (1, 4, 1, convert_to('<?xml version="1.0" encoding="UTF-8"?>
            <definitions xmlns="https://www.omg.org/spec/DMN/20191111/MODEL/" xmlns:dmndi="https://www.omg.org/spec/DMN/20191111/DMNDI/" xmlns:dc="http://www.omg.org/spec/DMN/20180521/DC/" id="Toekenning_Wmovoorziening" name="Toekenning Wmo-voorziening" namespace="http://camunda.org/schema/1.0/dmn" exporter="dmn-js (https://demo.bpmn.io/dmn)" exporterVersion="17.2.0">
              <decision id="decision_19w0x1w" name="">
                <decisionTable id="decisionTable_16f4dkd">
                  <input id="input1" label="">
                    <inputExpression id="inputExpression1" typeRef="string">
                      <text></text>
                    </inputExpression>
                  </input>
                  <output id="output1" label="" name="" typeRef="string" />
                </decisionTable>
              </decision>
              <dmndi:DMNDI>
                <dmndi:DMNDiagram id="DMNDiagram_0k7uk6v">
                  <dmndi:DMNShape id="DMNShape_1tqq611" dmnElementRef="decision_19w0x1w">
                    <dc:Bounds height="80" width="180" x="150" y="80" />
                  </dmndi:DMNShape>
                </dmndi:DMNDiagram>
              </dmndi:DMNDI>
            </definitions>', 'UTF8'), null, null, 'Mark Akkermans', '2025-09-24 14:16:56.476680');

INSERT INTO public.environments (id, name) VALUES (1, 'Test');
INSERT INTO public.environments (id, name) VALUES (2, 'Acceptatie');
INSERT INTO public.environments (id, name) VALUES (3, 'Pre-prod');
INSERT INTO public.environments (id, name) VALUES (4, 'Productie');

INSERT INTO public.deployments (id, dmn_id, dmn_version, environment_id, deployed_by, deployed_date) VALUES (1, 1, 1, 1, 'Mark Akkermans', '2025-09-24 15:22:13.123456');
INSERT INTO public.deployments (id, dmn_id, dmn_version, environment_id, deployed_by, deployed_date) VALUES (2, 1, 2, 2, 'Mark Akkermans', '2025-09-24 15:23:45.654321');
INSERT INTO public.deployments (id, dmn_id, dmn_version, environment_id, deployed_by, deployed_date) VALUES (3, 1, 3, 3, 'Mark Akkermans', '2025-09-24 15:25:00.111213');
