// Utilitário para mostrar/ocultar loader
function toggleLoader(ativo) {
  const loader = document.getElementById("loader");
  loader.style.display = ativo ? "flex" : "none";
}

// ➕ Adicionar campos de comparação dinâmica
const grupo = document.getElementById("grupoComparacoes");
document.getElementById("btnAdicionarPar").addEventListener("click", () => {
  const novo = document.createElement("div");
  novo.classList.add("campoComparacao");
  novo.innerHTML = `
    <label>📌 Produto T1: <input type="text" name="colProd1[]" placeholder="Ex: ITEM_COMPONENTE" required></label>
    <label>🔢 Quantidade T1: <input type="text" name="colQtd1[]" placeholder="Ex: QTDE" required></label>
    <label>📌 Produto T2: <input type="text" name="colProd2[]" placeholder="Ex: Código Item" required></label>
    <label>🔢 Quantidade T2: <input type="text" name="colQtd2[]" placeholder="Ex: Quantidade" required></label>
  `;
  grupo.appendChild(novo);
});

// 📊 Comparação personalizada
document.getElementById("formComparacao").addEventListener("submit", async (e) => {
  e.preventDefault();
  toggleLoader(true);

  const formData = new FormData(e.target);
  const resultadoDiv = document.getElementById("resultado");

  try {
    const response = await fetch("/api/comparar-dinamico", {
      method: "POST",
      body: formData
    });

    if (!response.ok) throw new Error();

    const dados = await response.json();
    resultadoDiv.innerHTML = "<h3>✅ Resultado da Comparação</h3>";
    dados.resultados.forEach((bloco, index) => {
      resultadoDiv.innerHTML += `
        <div class="blocoResultado">
          <h4>🔁 Comparação ${index + 1}</h4>
          <strong>🧞 Em ambas:</strong> ${bloco.emAmbas.join(", ")}<br>
          <strong>📄 Só na Tabela 1:</strong> ${bloco.apenasNaTabela1.join(", ")}<br>
          <strong>📅 Só na Tabela 2:</strong> ${bloco.apenasNaTabela2.join(", ")}<br>
        </div>
      `;
    });
    Swal.fire("✅ Comparação concluída!", "", "success");
  } catch {
    resultadoDiv.innerHTML = "❌ Erro ao processar os arquivos.";
    Swal.fire("Erro!", "Falha na comparação", "error");
  } finally {
    toggleLoader(false);
  }
});

// 🧮 Varredura entre planilhas
document.getElementById("formVarredura").addEventListener("submit", async (e) => {
  e.preventDefault();
  toggleLoader(true);

  const form = e.target;
  const formData = new FormData(form);
  formData.append("procurarTodaLinha", document.getElementById("procurarTodaLinha").checked);

  const resultadoVarredura = document.getElementById("resultadoVarredura");

  try {
    const response = await fetch("/api/varredura", {
      method: "POST",
      body: formData
    });

    if (!response.ok) throw new Error();

    const resultado = await response.json();
    resultadoVarredura.innerHTML = `
      <h3>📋 Resultado da Varredura</h3>
      <details open><summary>🧞 Em Ambas</summary><p>${resultado.emAmbas.join(", ")}</p></details>
      <details><summary>📄 Só na Tabela 1</summary><p>${resultado.apenasNaTabela1.join(", ")}</p></details>
      <details><summary>📅 Só na Tabela 2</summary><p>${resultado.apenasNaTabela2.join(", ")}</p></details>
      <details><summary>⚠️ Quantidade Divergente</summary><p>${resultado.quantidadeDiferente.join(", ")}</p></details>
    `;
    Swal.fire("📋 Varredura finalizada!", "", "success");
  } catch {
    resultadoVarredura.innerHTML = "❌ Erro na varredura.";
    Swal.fire("Erro!", "Falha na varredura", "error");
  } finally {
    toggleLoader(false);
  }
});

// 📦 Varredura por quantidade de item
document.getElementById("formQuantidadeItem").addEventListener("submit", async (e) => {
  e.preventDefault();
  toggleLoader(true);

  const form = e.target;
  const formData = new FormData(form);
  formData.append("modoHibrido", document.getElementById("modoHibrido").checked);

  const divResultado = document.getElementById("resultadoQuantidade");

  try {
    const response = await fetch("/api/varredura-item-quantidade", {
      method: "POST",
      body: formData
    });

    if (!response.ok) throw new Error();

    const resultado = await response.json();
    let html = `
      <h3>📦 Resultado da Quantidade do Item</h3>
      <p><strong>🔎 Item buscado:</strong> ${resultado.itemBuscado}</p>
      <p><strong>📊 Total de ocorrências:</strong> ${resultado.totalOcorrencias}</p>
      <p><strong>🧮 Soma total da quantidade:</strong> ${resultado.somaQuantidade}</p>
      <details open>
        <summary>📜 Detalhes das ocorrências:</summary>
        <ul>
    `;
    resultado.ocorrencias.forEach(o => {
      html += `<li>📌 Linha ${o.numeroLinha}: ${o.linhaCompleta} → Quantidade: <strong>${o.quantidadeEncontrada}</strong></li>`;
    });
    html += `</ul></details>`;
    divResultado.innerHTML = html;
    Swal.fire("📦 Varredura concluída!", "", "success");
  } catch {
    divResultado.innerHTML = "❌ Erro na análise de quantidade do item.";
    Swal.fire("Erro!", "Falha na varredura de item", "error");
  } finally {
    toggleLoader(false);
  }
});

// 📅 Exportar XLSX
function baixarXLSX() {
  Swal.fire("⏳ Gerando planilha...", "", "info");
  window.open("/api/exportar-xlsx", "_blank");
}

// 🖨️ Exportar PDF
function baixarPDF() {
  Swal.fire("⏳ Gerando PDF...", "", "info");
  window.open("/api/exportar-pdf", "_blank");
}
