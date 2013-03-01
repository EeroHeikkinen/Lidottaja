<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<td class="docs"> 
<form:form action="mapping/map" method="post" cssClass="inline">
	<h1>Tervetuloa LIDOttimeen</h1>
	<p>
	 	Tämä työkalu auttaa sinua luomaan LIDO-mappauksen järjestelmääsi. Työkalu tekee sinulle määrittelyt, jotka voit antaa järjestelmäntoimittajallesi.
	</p>
	<hr/> 
	<p>
		Työkalua käytetään seuraavien vaiheiden kautta:
	</p>
	<ul>
		<li>Teet mappauksen aineistotyypille</li>
		<li>Annat esimerkki-tietueen, jotta näet miten mappaus toimii</li>
		<li>Toimitat spesifikaatiot järjestelmäntoimittajallesi</li>
	</ul>
	<p>
		<button type="submit" id="proceed" name="_eventId_proceed">Aloita</button>
	</p>
</form:form>
</td>
<td class="code"></td> 