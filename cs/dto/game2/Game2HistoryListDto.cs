using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class Game2HistoryListDto : SerializeMessage {
	[ProtoMember(1)]
	public List<Game2HistoryDto> history;
}
