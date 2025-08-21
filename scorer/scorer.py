from flask import Flask, request, jsonify

app = Flask(__name__)

@app.route("/score", methods=["POST"])
def score_transaction():
    data = request.json
    amount = data.get("amount", 0)
    country = data.get("country", "")
    device_id = data.get("deviceId", "")

    # Simple dummy rules for scoring
    score = 0
    if amount > 100000:
        score += 80
    if country not in ["IN", "US", "UK"]:
        score += 50
    if device_id.startswith("D-"):
        score += 10

    decision = "APPROVED"
    if score > 100:
        decision = "DECLINED"
    elif 50 < score <= 100:
        decision = "CHALLENGE"

    return jsonify({
        "score": score,
        "decision": decision
    })

if __name__ == "__main__":
    app.run(port=5001, debug=True)
