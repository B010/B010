# Reels Blocker 🚫

App Android gratuito e open source que bloqueia **Reels do Instagram** e **Shorts do YouTube** (semelhante ao Socialite).
O resto dos apps (feed, stories, DMs, vídeos normais) continua funcionando.

## Como funciona
Usa um *Serviço de Acessibilidade* que observa apenas o Instagram e o YouTube. Quando a tela de Reels/Shorts aparece, o app aperta "voltar" automaticamente. Nada é coletado nem enviado para a internet (o app nem tem permissão de internet).

## Instalar
1. Vá em **Actions → Build Reels Blocker APK** no GitHub, abra a última execução e baixe o artefato `reels-blocker-apk`.
2. Instale o APK no celular (permita "fontes desconhecidas").
3. Abra o app → **Abrir configurações de Acessibilidade** → *Reels Blocker* → Ativar.
   - Android 13+: se aparecer "configuração restrita", vá em Configurações → Apps → Reels Blocker → ⋮ → **Permitir configurações restritas**.

## Se parar de funcionar
Instagram/YouTube às vezes mudam os IDs das telas. Atualize as listas em `BlockerService.kt`
(dá para descobrir os IDs novos com o app "Developer Assistant" ou `uiautomator dump`).
